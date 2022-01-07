package com.freelanceStats.commons.streamMaintainer

import akka.Done
import akka.actor.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior, SupervisorStrategy}
import akka.actor.typed.scaladsl.adapter._
import akka.actor.typed.scaladsl.AskPattern._
import akka.pattern.StatusReply
import akka.stream.javadsl.RunnableGraph
import akka.stream.{KillSwitch, Materializer}
import com.freelanceStats.commons.streamMaintainer.StreamMaintainer.Actor

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.FiniteDuration
import scala.util.{Failure, Success}

trait StreamMaintainer {
  def system: ActorSystem
  implicit val materializer: Materializer
  implicit val executionContext: ExecutionContext
  implicit val timeout: FiniteDuration

  def runnableGraph: RunnableGraph[(KillSwitch, Future[Done])]
  def actorRef: ActorRef[Actor.Command] = system.spawn(
    StreamMaintainer.Actor(runnableGraph),
    "stream-maintainer-manager"
  )

  def start(): Future[Done] =
    actorRef
      .ask[StatusReply[Done]](StreamMaintainer.Actor.Start.apply)(
        timeout,
        system.toTyped.scheduler
      )
      .map {
        case reply if reply.isSuccess =>
          reply.getValue
        case reply =>
          throw reply.getError
      }

  def stop(): Future[Done] =
    actorRef
      .ask[StatusReply[Done]](StreamMaintainer.Actor.Stop.apply)(
        timeout,
        system.toTyped.scheduler
      )
      .map {
        case reply if reply.isSuccess =>
          reply.getValue
        case reply =>
          throw reply.getError
      }
}

object StreamMaintainer {
  object Actor {

    def apply(
        runnableGraph: RunnableGraph[(KillSwitch, Future[Done])]
    )(implicit
        materializer: Materializer
    ): Behavior[Command] = Behaviors
      .supervise(stopped(runnableGraph))
      .onFailure(SupervisorStrategy.restart)

    private def stopped(
        runnableGraph: RunnableGraph[(KillSwitch, Future[Done])]
    )(implicit
        materializer: Materializer
    ): Behavior[Command] = Behaviors.setup { implicit context =>
      Behaviors.receiveMessagePartial {
        case Start(replyTo) =>
          context.log.info("Starting the stream")
          val (killSwitch, completionF) = runnableGraph.run(materializer)
          context.pipeToSelf(completionF) {
            case Failure(exception) =>
              HandleError(exception)
            case Success(_) =>
              HandleSuccessfulFinish
          }
          replyTo ! StatusReply.ack()
          running(runnableGraph, killSwitch)
        case Restart =>
          context.log.info("Restarting the stream")
          val (killSwitch, completionF) = runnableGraph.run(materializer)
          context.pipeToSelf(completionF) {
            case Failure(exception) =>
              HandleError(exception)
            case Success(_) =>
              HandleSuccessfulFinish
          }
          running(runnableGraph, killSwitch)
      }
    }

    private def running(
        runnableGraph: RunnableGraph[(KillSwitch, Future[Done])],
        killSwitch: KillSwitch
    )(implicit
        materializer: Materializer
    ): Behavior[Command] = Behaviors.setup { implicit context =>
      Behaviors.receiveMessagePartial {
        case HandleError(throwable) =>
          context.log.error(
            "Stream finished with error, restarting...",
            throwable
          )
          context.self ! Restart
          stopped(runnableGraph)
        case HandleSuccessfulFinish =>
          context.log.info("Stream finished successfully, restarting stream...")
          context.self ! Restart
          stopped(runnableGraph)
        case Stop(replyTo) =>
          context.log.info("Received Stop command, stopping actor and stream")
          killSwitch.shutdown()
          stopping(replyTo)
      }
    }

    private def stopping(
        replyTo: ActorRef[StatusReply[Done]]
    )(implicit
        materializer: Materializer
    ): Behavior[Command] = Behaviors.setup { implicit context =>
      Behaviors.receiveMessagePartial {
        case HandleError(throwable) =>
          context.log.warn(
            "Stream finished with error, stopping the actor...",
            throwable
          )
          replyTo ! StatusReply.error(throwable)
          Behaviors.stopped
        case HandleSuccessfulFinish =>
          context.log.info(
            "Stream finished successfully, stopping the actor..."
          )
          replyTo ! StatusReply.ack()
          Behaviors.stopped
      }
    }

    sealed trait Command
    case class Start(replyTo: ActorRef[StatusReply[Done]]) extends Command
    case object Restart extends Command
    case class Stop(replyTo: ActorRef[StatusReply[Done]]) extends Command
    case object HandleSuccessfulFinish extends Command
    case class HandleError(throwable: Throwable) extends Command
  }
}

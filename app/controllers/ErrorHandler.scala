package controllers

import java.util.concurrent.{CompletableFuture, CompletionStage}

import javax.inject.Singleton
import play.http.HttpErrorHandler
import play.mvc._


@Singleton class ErrorHandler extends HttpErrorHandler {
  override def onClientError(request: Http.RequestHeader, statusCode: Int, message: String): CompletionStage[Result] = CompletableFuture.completedFuture(Results.status(statusCode, "A client error occurred: " + message))

  override def onServerError(request: Http.RequestHeader, exception: Throwable): CompletionStage[Result] = CompletableFuture.completedFuture(Results.internalServerError("A server error occurred: " + exception.getMessage))
}
package com.xinh.domain.exception

sealed class Failure {
    object NetworkConnection : Failure()

    class ServerError(e: Error? = null) : Failure()

    class Unknown(e: Throwable) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
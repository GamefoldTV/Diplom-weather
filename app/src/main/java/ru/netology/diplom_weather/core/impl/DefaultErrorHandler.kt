package ru.netology.diplom_weather.core.impl

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import ru.netology.diplom_weather.R
import ru.netology.diplom_weather.core.CommonUi
import ru.netology.diplom_weather.core.ErrorHandler
import ru.netology.diplom_weather.core.Logger
import ru.netology.diplom_weather.core.Resources

fun createDefaultGlobalScope(): CoroutineScope {
	val errorHandler = CoroutineExceptionHandler { _, exception ->
		Log.e("DefaultGlobalScope", "Error", exception)
	}
	return CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
}
class DefaultErrorHandler(
	private val appContext: Context,
	private val logger: Logger = AndroidLogger(),
	private val commonUi: CommonUi = AndroidCommonUi(appContext),
	private val resources: Resources = AndroidResources(appContext),
	private val globalScope: CoroutineScope = createDefaultGlobalScope(),
) : ErrorHandler {

	private var lastRestartTimestamp = 0L

	override fun handleError(exception: Throwable) {
		logger.err(exception)
		when (exception) {
			is AuthException -> handleAuthException(exception)
			is ConnectionException -> handleConnectionException(exception)
			is StorageException -> handleStorageException(exception)
			is RemoteServiceException -> handleRemoteServiceException(exception)
			is UserFriendlyException -> handleUserFriendlyException(exception)
			is TimeoutCancellationException -> handleTimeoutException(exception)
			is CancellationException -> return
			else -> handleUnknownException()
		}
	}

	override fun getUserMessage(exception: Throwable): String {
		return when (exception) {
			is AuthException -> resources.getString(R.string.core_common_session_expired)
			is ConnectionException -> resources.getString(R.string.core_common_error_connection)
			is StorageException -> resources.getString(R.string.core_common_error_storage)
			is TimeoutCancellationException -> resources.getString(R.string.core_common_error_timeout)
			is RemoteServiceException -> getRemoteServiceExceptionMessage(exception)
			is UserFriendlyException -> exception.userFriendlyMessage
			else -> resources.getString(R.string.core_common_error_message)
		}
	}

	private fun getRemoteServiceExceptionMessage(exception: RemoteServiceException): String {
		val message = exception.message
		return if (message?.isNotBlank() == true) {
			message
		} else {
			resources.getString(R.string.core_common_error_service)
		}
	}

	private fun handleAuthException(exception: AuthException) {
		val currentTimestamp = System.currentTimeMillis()
		if (currentTimestamp - lastRestartTimestamp > RESTART_TIMEOUT) {
			commonUi.toast(getUserMessage(exception))
			lastRestartTimestamp = currentTimestamp
		}
	}

	private fun handleConnectionException(exception: ConnectionException) {
		commonUi.toast(getUserMessage(exception))
	}

	private fun handleStorageException(exception: StorageException) {
		commonUi.toast(getUserMessage(exception))
	}

	private fun handleTimeoutException(exception: TimeoutCancellationException) {
		commonUi.toast(getUserMessage(exception))
	}

	private fun handleRemoteServiceException(exception: RemoteServiceException) {
		showErrorDialog(getRemoteServiceExceptionMessage(exception))
	}

	private fun handleUserFriendlyException(exception: UserFriendlyException) {
		showErrorDialog(exception.userFriendlyMessage)
	}

	private fun handleUnknownException() {
		showErrorDialog(resources.getString(R.string.core_common_error_message))
	}

	private fun showErrorDialog(message: String) {
		globalScope.launch {
			commonUi.alertDialog(
				AlertDialogConfig(
					title = resources.getString(R.string.core_common_error_title),
					message = message,
					positiveButton = resources.getString(R.string.core_common_action_ok),
				)
			)
		}
	}

	private companion object {
		const val RESTART_TIMEOUT = 5000
	}
}
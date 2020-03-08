package com.xinh.authentication.login

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.xinh.authentication.R
import com.xinh.authentication.databinding.FragmentLoginBinding
import com.xinh.authentication.login.component.LoginHandlerImpl
import com.xinh.authentication.login.listener.OnLoginClickBackListener
import com.xinh.authentication.login.listener.OnLoginSuccessListener
import com.xinh.authentication.login.listener.OnOpenRegisterListener
import com.xinh.domain.interactor.Login
import com.xinh.domain.model.UserType
import com.xinh.koininjection.AUTHENTICATION_SCOPE_ID
import com.xinh.koininjection.AUTHENTICATION_SCOPE_NAME
import com.xinh.presentation.authentication.AuthenticationViewModel
import com.xinh.share.BaseFragment
import com.xinh.share.extension.getCompatColor
import com.xinh.share.extension.getViewModelScope
import com.xinh.share.extension.observeExt
import com.xinh.share.spanner.Spanner
import com.xinh.share.spanner.Spans.bold
import com.xinh.share.spanner.Spans.click
import com.xinh.share.spanner.Spans.foreground

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val authenticationViewModel =
            getViewModelScope<AuthenticationViewModel>(
                    AUTHENTICATION_SCOPE_ID,
                    AUTHENTICATION_SCOPE_NAME
            )

    private var onLoginClickBackListener: OnLoginClickBackListener? = null
    private var onLoginSuccessListener: OnLoginSuccessListener? = null
    private var onOpenRegisterListener: OnOpenRegisterListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnLoginClickBackListener) onLoginClickBackListener = context
        if (context is OnLoginSuccessListener) onLoginSuccessListener = context
        if (context is OnOpenRegisterListener) onOpenRegisterListener = context
    }

    override fun getLayout(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        viewBinding?.run {
            onClickBackListener = View.OnClickListener {
                onLoginClickBackListener?.onSignInClickBack()
            }

            LoginHandlerImpl().run {
                init(requireContext(), llContent)

                setOnLoginClickListener { email, password ->
                    authenticationViewModel?.login(
                            Login.Params(
                                    email,
                                    password
                            )
                    )
                }
            }

            onClickLoginFacebookListener = View.OnClickListener {
                authenticationViewModel?.login(Login.Params(type = UserType.Facebook))
            }

            onClickLoginGoogleListener = View.OnClickListener {
                authenticationViewModel?.login(Login.Params(type = UserType.Google))
            }

            initTextNoAccountAndSignUp(tvNoAccountAndSignUp)
        }
    }

    private fun initTextNoAccountAndSignUp(tvNoAccountAndSignUp: AppCompatTextView) {
        tvNoAccountAndSignUp.setOnClickListener {
            onOpenRegisterListener?.openRegister()
        }

        tvNoAccountAndSignUp.text = Spanner()
                .append(getString(R.string.txt_dont_have_an_account))
                .append(" ")
                .append(
                        getString(R.string.txt_sign_up),
                        bold(),
                        foreground(requireContext().getCompatColor(R.color.blue)),
                        click(View.OnClickListener {
                            onOpenRegisterListener?.openRegister()
                        })
                )
    }

    override fun intiObserveViewModel() {
        authenticationViewModel?.run {
            onLoginSuccess.observeExt(this@LoginFragment) {
                onLoginSuccessListener?.onLoginSuccess()
            }

            onLoginFail.observeExt(this@LoginFragment) {
                Toast.makeText(requireContext(), "Login fail....", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
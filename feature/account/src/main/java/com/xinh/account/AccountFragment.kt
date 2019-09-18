package com.xinh.account

import android.content.Context
import android.view.View
import com.xinh.account.databinding.FragmentAccountBinding
import com.xinh.koininjection.ACCOUNT_SCOPE_ID
import com.xinh.koininjection.ACCOUNT_SCOPE_NAME
import com.xinh.presentation.account.AccountViewModule
import com.xinh.share.BaseFragment
import com.xinh.share.extension.getOrCreateScope
import com.xinh.share.extension.getViewModelScope
import com.xinh.share.extension.observeExt
import org.koin.androidx.scope.bindScope

class AccountFragment : BaseFragment<FragmentAccountBinding>() {
    private val accountViewModuleScope =
        getOrCreateScope(ACCOUNT_SCOPE_ID, ACCOUNT_SCOPE_NAME)

    private val accountViewModule =
        accountViewModuleScope.getViewModelScope<AccountViewModule>()

    private var onLogoutSuccessListener: OnLogoutSuccessListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLogoutSuccessListener) onLogoutSuccessListener = context
    }

    override fun getLayout(): Int {
        return R.layout.fragment_account
    }

    override fun onBindScope() {
        super.onBindScope()

        bindScope(accountViewModuleScope)
    }

    override fun initView() {
        viewBinding?.run {
            onLogoutClickListener = View.OnClickListener {
                accountViewModule?.logout()
            }
        }
    }

    override fun intiObserveViewModel() {
        accountViewModule?.run {
            onLogoutSuccess.observeExt(this@AccountFragment) {
                onLogoutSuccessListener?.onLogoutSuccess()
            }
        }
    }

}
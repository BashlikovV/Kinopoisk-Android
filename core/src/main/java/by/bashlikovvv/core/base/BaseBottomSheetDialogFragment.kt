package by.bashlikovvv.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<ViewBindingType : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: ViewBindingType? = null

    val binding: ViewBindingType
        get() = requireNotNull(_binding)

    val bindingOrNull: ViewBindingType?
        get() = _binding

    abstract fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ViewBindingType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupViewBinding(inflater, container)
        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
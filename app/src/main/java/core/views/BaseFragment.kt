package core.views

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * Base class for all fragments
 */
abstract class BaseFragment : Fragment() {

    abstract val viewModel: ViewModel

}
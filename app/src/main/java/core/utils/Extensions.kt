package core.utils

import android.content.Context
import android.util.TypedValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import ru.nesterov.diceroller.app.domain.entity.Dice

fun Dice.isNotDefault(): Boolean =
     this.faces != 4 && this.faces != 6 && this.faces != 8 && this.faces != 10 && this.faces != 12 && this.faces != 20

fun NavController.getCurrentBackStackEntryLiveData(lifecycleOwner: LifecycleOwner, key: String, observer: (Boolean) -> Unit) {
     currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(key)?.observe(
          lifecycleOwner,
          Observer(observer)
     )
}

fun NavController.setSavedStateBoolean(key: String, value: Boolean) {
     previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
     TypedValue.COMPLEX_UNIT_DIP,
     this.toFloat(),
     context.resources.displayMetrics
).toInt()

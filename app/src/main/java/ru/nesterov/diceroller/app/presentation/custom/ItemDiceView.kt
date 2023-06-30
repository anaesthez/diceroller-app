package ru.nesterov.diceroller.app.presentation.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.withStyledAttributes
import core.utils.toDp
import core.views.OnItemDiceClickListener
import ru.nesterov.diceroller.R
import ru.nesterov.diceroller.databinding.ItemDiceBinding

/**
 * Custom view
 */
class ItemDiceView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
): FrameLayout(context, attrs, defStyleAttr, defStyleRes){

    private val binding: ItemDiceBinding

    private var listener: OnItemDiceClickListener? = null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        0
    )
    constructor(context: Context) : this(
        context,
        null
    )

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.item_dice, this, true)
        binding = ItemDiceBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr)
        initListeners()
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs == null) return

        context.withStyledAttributes(attrs, R.styleable.ItemDiceView, defStyleAttr) {

           getString(R.styleable.ItemDiceView_diceText).also {
                setDiceText(it)
            }
            getResourceId(R.styleable.ItemDiceView_diceImage, R.drawable.dice20_empty).also {
                setDiceImage(it)
            }
            getColor(R.styleable.ItemDiceView_diceTextColor, Color.WHITE).also {
                setDiceTextColor(it)
            }
            getDimension(R.styleable.ItemDiceView_diceTextSize, 0F).also {
                setDiceTextSize(it)
            }
            getInt(R.styleable.ItemDiceView_diceImageScaleType, 0).also {
                setDiceScaleType(it)
            }

            val diceImageHeight = getDimension(R.styleable.ItemDiceView_diceHeight, 300f)
            val diceImageWidth = getDimension(R.styleable.ItemDiceView_diceWidth, 300f)
            setDiceSizes(diceImageHeight.toInt(), diceImageWidth.toInt())

        }
    }

    private fun setDiceTextColor(color: Int) {
        binding.diceText.setTextColor(color)
    }

    private fun setDiceSizes(imageHeight: Int, imageWidth: Int) {
        with(binding.diceImage) {
            layoutParams?.height = imageHeight
            layoutParams?.width = imageWidth
        }
    }


    private fun initListeners() {
        binding.diceImage.setOnClickListener {
            this.listener?.invoke()
        }
        binding.diceText.setOnClickListener {
            this.listener?.invoke()
        }

    }

    fun setDiceTextSize(textSize: Float) {
        binding.diceText.textSize = textSize
    }

    fun setDicePadding(start: Int, top: Int, end: Int, bottom: Int) {
        binding.diceText.setPadding(
            start.toDp(context),
            top.toDp(context),
            end.toDp(context),
            bottom.toDp(context)
        )
    }

    fun setListener(listener: OnItemDiceClickListener?) {
        this.listener = listener
    }

    private fun setDiceScaleType(scaleType: Int) {
            when (scaleType) {
                0 -> binding.diceImage.scaleType = ImageView.ScaleType.FIT_CENTER
                1 -> binding.diceImage.scaleType = ImageView.ScaleType.CENTER_CROP
                2 -> binding.diceImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
                3 -> binding.diceImage.scaleType = ImageView.ScaleType.FIT_END
                4 -> binding.diceImage.scaleType = ImageView.ScaleType.FIT_START
                5 -> binding.diceImage.scaleType = ImageView.ScaleType.FIT_XY
                6 -> binding.diceImage.scaleType = ImageView.ScaleType.MATRIX
            }
    }

    fun setDiceText(text: String?) {
        binding.diceText.text = text ?: "tap"
    }

    fun setDiceImage(diceImage: Int?) {
        diceImage?.let {
            binding.diceImage.setImageResource(it)
        } ?: R.drawable.dice20_empty
    }
}
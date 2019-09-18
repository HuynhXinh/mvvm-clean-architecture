package com.xinh.share.spanner

import android.app.PendingIntent
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.EmbossMaskFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.LocaleList
import android.text.Layout
import android.text.style.*
import android.text.style.ImageSpan
import android.view.View
import androidx.annotation.*
import java.util.*

object Spans {

    fun sizePX(@Dimension(unit = Dimension.PX) px: Int): Span {
        return Span(AbsoluteSizeSpanBuilder(px, false))
    }

    fun sizeDP(@Dimension(unit = Dimension.DP) dp: Int): Span {
        return Span(AbsoluteSizeSpanBuilder(dp, true))
    }

    fun sizeSP(@Dimension(unit = Dimension.SP) sp: Int): Span {
        return Span(AbsoluteSizeSpanBuilder(sp, true))
    }

    fun scaleSize(@FloatRange(from = 0.0) proportion: Float): Span {

        return Span(object : SpanBuilder {
            override fun build(): Any {
                return RelativeSizeSpan(proportion)
            }
        })
    }

    fun scaleXSize(@FloatRange(from = 0.0) proportion: Float): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return ScaleXSpan(proportion)
            }
        })
    }

    fun bold(): Span {
        return Span(StyleSpanBuilder(Typeface.BOLD))
    }

    fun italic(): Span {
        return Span(StyleSpanBuilder(Typeface.ITALIC))
    }

    fun boldItalic(): Span {
        return Span(StyleSpanBuilder(Typeface.BOLD_ITALIC))
    }

    fun font(font: String): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return TypefaceSpan(font)
            }
        })
    }

    fun customFont(font: Typeface?): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return font?.let { CustomTypefaceSpan("", it) } ?: font("")
            }
        })
    }

    fun strikeThrough(): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return StrikethroughSpan()
            }
        })
    }

    fun underline(): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return UnderlineSpan()
            }
        })
    }

    fun background(@ColorInt color: Int): Span {
        return Span(ColorSpanBuilder(ColorSpanBuilder.BACKGROUND, color))
    }

    fun foreground(@ColorInt color: Int): Span {
        return Span(ColorSpanBuilder(ColorSpanBuilder.FOREGROUND, color))
    }

    fun subscript(): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return SubscriptSpan()
            }
        })
    }

    fun superscript(): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return SuperscriptSpan()
            }
        })
    }

    fun image(drawable: Drawable): com.xinh.share.spanner.ImageSpan {
        return ImageSpan(object : SpanBuilder {
            override fun build(): Any {
                return ImageSpan(drawable)
            }
        })
    }

    fun image(drawable: Drawable, verticalAlignment: Int): com.xinh.share.spanner.ImageSpan {
        return ImageSpan(object : SpanBuilder {
            override fun build(): Any {
                return ImageSpan(drawable, verticalAlignment)
            }
        })
    }

    fun image(context: Context, @DrawableRes drawableId: Int, verticalAlignment: Int): com.xinh.share.spanner.ImageSpan {
        return ImageSpan(object : SpanBuilder {
            override fun build(): Any {
                return ImageSpan(context, drawableId, verticalAlignment)
            }
        })
    }

    fun image(context: Context, @DrawableRes drawableId: Int): com.xinh.share.spanner.ImageSpan {
        return ImageSpan(object : SpanBuilder {
            override fun build(): Any {
                return ImageSpan(context, drawableId)
            }
        })
    }

    fun quote(): Span {
        return Span(QuoteSpanBuilder(null))
    }

    fun quote(@ColorInt color: Int): Span {
        return Span(QuoteSpanBuilder(color))
    }

    @JvmOverloads
    fun image(context: Context, bitmap: Bitmap, verticalAlignment: Int = ImageSpan.ALIGN_BOTTOM): Span {
        return ImageSpan(object : SpanBuilder {
            override fun build(): Any {
                return ImageSpan(context, bitmap, verticalAlignment)
            }
        })
    }

    fun custom(builder: SpanBuilder): Span {
        return Span(builder)
    }

    fun click(onClickListener: View.OnClickListener): Span {
        return Span(ClickSpanBuilder(onClickListener))
    }

    fun url(url: String): Span {
        return ImageSpan(object : SpanBuilder {
            override fun build(): Any {
                return URLSpan(url)
            }
        })
    }

    fun center(): Span {
        return Span(AlignmentSpanBuilder(Layout.Alignment.ALIGN_CENTER))
    }

    fun alignmentOpposite(): Span {
        return Span(AlignmentSpanBuilder(Layout.Alignment.ALIGN_OPPOSITE))
    }

    fun alignmentNormal(): Span {
        return Span(AlignmentSpanBuilder(Layout.Alignment.ALIGN_NORMAL))
    }

    fun bullet(gapWidth: Int, @ColorInt color: Int): Span {
        return Span(BulletSpanBuilder(gapWidth, color))
    }

    fun bullet(gapWidth: Int): Span {
        return Span(BulletSpanBuilder(gapWidth, null))
    }

    fun bullet(): Span {
        return Span(BulletSpanBuilder(null, null))
    }

    fun imageMargin(drawable: Drawable): Span {
        return Span(ImageSpanBuilder(drawable, null, null))
    }

    fun imageMargin(drawable: Drawable, pad: Int): Span {
        return Span(ImageSpanBuilder(drawable, null, pad))
    }

    fun edit(): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return EasyEditSpan()
            }
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun edit(pendingIntent: PendingIntent): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return EasyEditSpan(pendingIntent)
            }
        })
    }

    fun imageMargin(image: Bitmap): Span {
        return Span(ImageSpanBuilder(null, image, null))
    }

    fun imageMargin(image: Bitmap, pad: Int): Span {
        return Span(ImageSpanBuilder(null, image, pad))
    }

    fun leadingMargin(first: Int, rest: Int): Span {
        return Span(LeadingMarginSpanBuilder(first, rest))
    }

    fun leadingMargin(every: Int): Span {
        return Span(LeadingMarginSpanBuilder(every, null))
    }

    fun blur(radius: Float, style: BlurMaskFilter.Blur): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return MaskFilterSpan(BlurMaskFilter(radius, style))
            }
        })
    }

    fun emboss(direction: FloatArray, ambient: Float, specular: Float, blurRadius: Float): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return MaskFilterSpan(EmbossMaskFilter(direction, ambient, specular, blurRadius))
            }
        })
    }

    fun tabStop(where: Int): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return TabStopSpan.Standard(where)
            }
        })
    }

    fun appearance(context: Context, appearance: Int): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return TextAppearanceSpan(context, appearance)
            }
        })
    }

    fun appearance(context: Context, appearance: Int, colorList: Int): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return TextAppearanceSpan(context, appearance, colorList)
            }
        })
    }

    fun appearance(family: String, style: Int, size: Int, color: ColorStateList, linkColor: ColorStateList): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return TextAppearanceSpan(family, style, size, color, linkColor)
            }
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun locale(locale: Locale): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return LocaleSpan(locale)
            }
        })
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    fun locale(localeList: LocaleList): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return LocaleSpan(localeList)
            }
        })
    }

    fun suggestion(context: Context, suggestions: Array<String>, flags: Int): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return SuggestionSpan(context, suggestions, flags)
            }
        })
    }

    fun suggestion(locale: Locale, suggestions: Array<String>, flags: Int): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return SuggestionSpan(locale, suggestions, flags)
            }
        })
    }

    fun suggestion(context: Context, locale: Locale, suggestions: Array<String>, flags: Int, notificationTargetClass: Class<*>): Span {
        return Span(object : SpanBuilder {
            override fun build(): Any {
                return SuggestionSpan(context, locale, suggestions, flags, notificationTargetClass)
            }
        })
    }
}

package org.michaelbel.moviemade.ui.view.cell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.michaelbel.core.extensions.Extensions;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.utils.ScreenUtils;

public class TextCell extends FrameLayout {

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_VALUE_TEXT = 1;
    public static final int MODE_SWITCH = 2;
    public static final int MODE_CHECKBOX = 3;
    public static final int MODE_COLOR = 4;
    public static final int MODE_ICON = 5;

    @IntDef({
            MODE_DEFAULT,
            MODE_VALUE_TEXT,
            MODE_SWITCH,
            MODE_CHECKBOX,
            MODE_COLOR,
            MODE_ICON
    })
    private @interface Mode {}

    protected TextView textView;
    private TextView valueText;
    private ImageView iconView;
    private SwitchCompat switchCompat;
    private AppCompatCheckBox checkBox;

    private int iconColor;
    private int cellHeight;

    protected Paint paint;
    private boolean divider;
    private Rect rect = new Rect();
    private int currentMode = MODE_DEFAULT;

    public TextCell(Context context) {
        super(context);

        iconColor = Theme.iconActiveColor();
        cellHeight = ScreenUtils.dp(48);

        setElevation(ScreenUtils.dp(1));
        setForeground(Extensions.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        iconView = new ImageView(context);
        iconView.setVisibility(INVISIBLE);
        iconView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 0, 0));
        addView(iconView);

        textView = new AppCompatTextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 16, 0));
        addView(textView);

        valueText = new AppCompatTextView(context);
        valueText.setLines(1);
        valueText.setMaxLines(1);
        valueText.setSingleLine();
        valueText.setVisibility(INVISIBLE);
        valueText.setEllipsize(TextUtils.TruncateAt.END);
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        valueText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        valueText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(valueText);

        switchCompat = new SwitchCompat(context);
        switchCompat.setClickable(false);
        switchCompat.setFocusable(false);
        switchCompat.setVisibility(INVISIBLE);
        switchCompat.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(switchCompat);

        checkBox = new AppCompatCheckBox(context);
        checkBox.setClickable(false);
        checkBox.setVisibility(INVISIBLE);
        checkBox.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(checkBox);

        changeSwitchTheme();
        setMode(currentMode);
    }

    public TextCell setIcon(@DrawableRes int icon) {
        iconView.setVisibility(icon == 0 ? INVISIBLE : VISIBLE);
        if (icon != 0) {
            iconView.setImageDrawable(Theme.getIcon(icon, ContextCompat.getColor(getContext(), iconColor)));
        }

        if (currentMode == MODE_ICON) {
            textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 56, 0, 16, 0));
        }

        return this;
    }

    public TextCell setText(@NonNull String text) {
        textView.setText(text);
        return this;
    }

    public TextCell setText(@StringRes int textId) {
        textView.setText(getContext().getText(textId));
        return this;
    }

    public TextCell setChecked(boolean value) {
        if (currentMode == MODE_SWITCH) {
            switchCompat.setChecked(value);
        }

        if (currentMode == MODE_CHECKBOX) {
            checkBox.setChecked(value);
        }

        return this;
    }

    public TextCell setValue(@NonNull String value) {
        valueText.setText(value);
        return this;
    }

    public TextCell setValue(@StringRes int textId) {
        valueText.setText(getContext().getText(textId));
        return this;
    }

    public TextCell setMode(@Mode int mode) {
        currentMode = mode;

        if (currentMode == MODE_DEFAULT) {
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_VALUE_TEXT) {
            valueText.setVisibility(VISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_SWITCH) {
            switchCompat.setVisibility(VISIBLE);
            valueText.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_CHECKBOX) {
            checkBox.setVisibility(VISIBLE);
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_COLOR) {
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_ICON) {
            iconView.setVisibility(VISIBLE);
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
        }

        return this;
    }

    public TextCell setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    public TextCell changeLayoutParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (ScreenUtils.isLandscape()) {
            params.leftMargin = ScreenUtils.dp(56);
            params.rightMargin = ScreenUtils.dp(56);
        }

        setLayoutParams(params);
        return this;
    }

    public TextView getTextView() {
        return textView;
    }

    public TextCell setIconColor(@ColorInt int color) {
        iconColor = color;
        return this;
    }

    public TextCell setHeight(int height) {
        cellHeight = height;
        return this;
    }

    private void changeSwitchTheme() {
        int thumbOn = ContextCompat.getColor(getContext(), Theme.thumbOnColor());
        int thumbOff = ContextCompat.getColor(getContext(), Theme.thumbOffColor());

        int trackOn = ContextCompat.getColor(getContext(), Theme.trackOnColor());
        int trackOff = ContextCompat.getColor(getContext(), Theme.trackOffColor());

        DrawableCompat.setTintList(switchCompat.getThumbDrawable(), new ColorStateList(
                new int[][]{
                        new int[]{ android.R.attr.state_checked },
                        new int[]{}
                },
                new int[]{
                        thumbOn,
                        thumbOff
                }));

        DrawableCompat.setTintList(switchCompat.getTrackDrawable(), new ColorStateList(
                new int[][]{
                        new int[]{ android.R.attr.state_checked  },
                        new int[]{}
                },
                new int[]{
                        trackOn,
                        trackOff
                }));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = cellHeight + (divider ? 1 : 0);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(getPaddingLeft(), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }
}
package org.michaelbel.moviemade.ui.view.cell;

/*
public class TextDetailCellDev extends LinearLayout {

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_SWITCH = 1;
    public static final int MODE_CHECKBOX = 2;

    @IntDef({
            MODE_DEFAULT,
            MODE_SWITCH,
            MODE_CHECKBOX
    })
    private @interface Mode {}

    private TextView textView;
    private TextView valueText;
    private SwitchCompat switchCompat;
    private AppCompatCheckBox checkBox;

    private RatingViewDev ratingView;

    private Paint paint;
    private boolean divider;
    private boolean multiline;
    private Rect rect = new Rect();
    private int currentMode = MODE_DEFAULT;

    private boolean ratingAdedd;

    public TextDetailCellDev(Context context) {
        super(context);

        this.multiline = true;
        setOrientation(VERTICAL);
        setElevation(ScreenUtils.dp(1));
        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(frameLayout);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START));
        frameLayout.addView(linearLayout);

        textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 10, 16, 0));
        linearLayout.addView(textView);

        valueText = new TextView(context);
        valueText.setLines(1);
        valueText.setMaxLines(1);
        valueText.setSingleLine();
        valueText.setEllipsize(TextUtils.TruncateAt.END);
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        valueText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        valueText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 2, 16, 10));
        linearLayout.addView(valueText);

        switchCompat = new SwitchCompat(context);
        switchCompat.setClickable(false);
        switchCompat.setVisibility(INVISIBLE);
        switchCompat.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        frameLayout.addView(switchCompat);

        switchCompat.setFocusableInTouchMode(false);
        switchCompat.clearFocus();

        //this.setFocusableInTouchMode(true);
        //this.requestFocus();

        checkBox = new AppCompatCheckBox(context);
        checkBox.setClickable(false);
        checkBox.setFocusable(false);
        checkBox.setVisibility(INVISIBLE);
        checkBox.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        frameLayout.addView(checkBox);

        ratingView = new RatingViewDev(context);
        ratingView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 14.5F, 0, 0, 10));

        changeSwitchTheme();
        setMode(currentMode);
    }

    public TextDetailCellDev setText(@NonNull String text) {
        textView.setText(text);
        return this;
    }

    public TextDetailCellDev setText(@StringRes int textId) {
        textView.setText(getContext().getText(textId));
        return this;
    }

    public TextDetailCellDev setValue(@NonNull String text) {
        valueText.setText(text);
        return this;
    }

    public TextDetailCellDev setValue(@StringRes int textId) {
        valueText.setText(getContext().getText(textId));
        return this;
    }

    public TextDetailCellDev setChecked(boolean value) {
        if (currentMode == MODE_SWITCH) {
            switchCompat.setChecked(value);
        } else if (currentMode == MODE_CHECKBOX) {
            checkBox.setChecked(value);
        }
        return this;
    }

    public TextDetailCellDev setMode(int mode) {
        currentMode = mode;

        if (currentMode == MODE_DEFAULT) {
            valueText.setVisibility(VISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_SWITCH) {
            valueText.setVisibility(VISIBLE);
            switchCompat.setVisibility(VISIBLE);
            checkBox.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_CHECKBOX) {
            valueText.setVisibility(VISIBLE);
            checkBox.setVisibility(VISIBLE);
            switchCompat.setVisibility(INVISIBLE);
        }
        return this;
    }

    public TextDetailCellDev setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    */
/*public void setMultiline(boolean value) {
        multiline = value;

        if (value) {
            valueText.setLines(0);
            valueText.setMaxLines(0);
            valueText.setSingleLine(false);
            valueText.setPadding(0, 0, 0, ScreenUtils.dp(12));
        } else {
            valueText.setLines(1);
            valueText.setMaxLines(1);
            valueText.setSingleLine();
            valueText.setPadding(0, 0, 0, 0);
        }
    }*//*


    public TextDetailCellDev changeLayoutParams() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if (ScreenUtils.isLandscape()) {
            params.leftMargin = ScreenUtils.dp(56);
            params.rightMargin = ScreenUtils.dp(56);
        }

        setLayoutParams(params);
        return this;
    }

    public TextDetailCellDev setRating(int stars) {
        if (stars == 0) {
            valueText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 2, 16, 10));
            removeView(ratingView);
            ratingAdedd = false;
        } else {
            valueText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 2, 16, 4));
            ratingView.setRating(stars);

            if (!ratingAdedd) {
                addView(ratingView);
                ratingAdedd = true;
            }
        }

        return this;
    }

    public String getTitleText() {
        return textView.getText().toString();
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

        int height;
        int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);

        if (multiline) {
            height = getMeasuredHeight();
        } else {
            height = ScreenUtils.dp(64) + (divider ? 1 : 0);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(getPaddingLeft(), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
        }
    }

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
}*/
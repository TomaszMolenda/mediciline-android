package local.tomo.medi.activity.drug;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.widget.RelativeLayout;

import local.tomo.medi.R;

class OverdueAnimation {

    private final ValueAnimator valueAnimator;

    OverdueAnimation(Context context, RelativeLayout relativeLayout) {

        int colorFrom = context.getResources().getColor(R.color.A82C6F);
        int colorTo = context.getResources().getColor(R.color.D34866);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250);
        colorAnimation.addUpdateListener(animator -> relativeLayout.setBackgroundColor((int) animator.getAnimatedValue()));
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);

        this.valueAnimator = colorAnimation;
    }

    void start() {

        valueAnimator.start();
    }
}

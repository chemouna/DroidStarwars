package com.mounacheikhna.ctc.ui.film;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.SharedElementCallback;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.StarWarsApp;
import com.mounacheikhna.ctc.lib.api.tmdb.FilmDetails;
import com.mounacheikhna.ctc.ui.view.CheckableFab;
import com.mounacheikhna.ctc.ui.view.ExpandingTextView;
import com.mounacheikhna.ctc.util.Animations.EmptyTransitionListener;
import com.mounacheikhna.ctc.util.Colors;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

import static com.mounacheikhna.ctc.ui.film.FilmActivity.FILM_EXTRA;
import static com.mounacheikhna.ctc.util.ApiLevels.isAtLeastLollipop;
import static com.mounacheikhna.ctc.util.ApiLevels.isAtLeastM;

/**
 * Created by mouna on 28/11/15.
 *
 * Displays a film : its poster, details with a different layout for handset and tablet.
 */
public class FilmDetailsFragment extends Fragment {

  @Bind(R.id.poster_image) ImageView mPosterImage;
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.collapsing_toolbar) @Nullable CollapsingToolbarLayout mCollapsingToolbarLayout;
  @Bind(R.id.description) ExpandingTextView mDescriptionTextView;
  @Bind(R.id.tv_rating_value) TextView mRatingValueView;
  @Bind(R.id.tv_rating_votes) TextView mRatingVotesView;
  @Bind(R.id.film_fab) CheckableFab mFilmFab;

  @Inject Picasso mPicasso;

  private Transition.TransitionListener mReturnTransitionListener = new EmptyTransitionListener() {
    @SuppressLint("NewApi") @Override public void onTransitionStart(Transition transition) {
      super.onTransitionStart(transition);
      mFilmFab.setVisibility(View.INVISIBLE);
    }
  };

  public static FilmDetailsFragment newInstance(FilmDetails film) {
    FilmDetailsFragment fragment = new FilmDetailsFragment();
    Bundle args = new Bundle();
    args.putParcelable(FILM_EXTRA, film);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_film_detail, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    StarWarsApp.get(getActivity()).getComponent().injectFilmDetailsFragment(this);
    setupToolbar();
    setupTransitions();
    final FilmDetails filmDetails = getArguments().getParcelable(FILM_EXTRA);
    display(filmDetails);
  }

  @SuppressLint("NewApi") private void setupTransitions() {
    if (isAtLeastLollipop()) {
      getActivity().setExitSharedElementCallback(new SharedElementCallback() {
        @Override public Parcelable onCaptureSharedElementSnapshot(View sharedElement,
            Matrix viewToGlobalMatrix, RectF screenBounds) {
          int bitmapWidth = Math.round(screenBounds.width());
          int bitmapHeight = Math.round(screenBounds.height());
          Bitmap bitmap = null;
          if (bitmapWidth > 0 && bitmapHeight > 0) {
            bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            sharedElement.draw(new Canvas(bitmap));
          }
          return bitmap;
        }
      });
      getActivity().getWindow()
          .getSharedElementReturnTransition()
          .addListener(mReturnTransitionListener);
    }
  }

  private void setupToolbar() {
    if (!(getActivity() instanceof AppCompatActivity)) {
      throw new IllegalArgumentException("Activity must inherit AppCompatActivity.");
    }
    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    activity.setSupportActionBar(mToolbar);
    if (activity.getSupportActionBar() != null) {
      activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  private void display(FilmDetails filmDetails) {
    if (mCollapsingToolbarLayout == null) {
      mToolbar.setTitle(filmDetails.movieResult.title);
      final AppCompatActivity activity = (AppCompatActivity) getActivity();
      activity.setSupportActionBar(mToolbar);
    } else {
      mCollapsingToolbarLayout.setTitle(filmDetails.movieResult.title);
    }
    mDescriptionTextView.setText(filmDetails.movieResult.overview);

    mRatingValueView.setText(filmDetails.getVotesAverage(getActivity()));
    mRatingVotesView.setText(filmDetails.getVotesValue(getActivity()));

    if (!TextUtils.isEmpty(filmDetails.posterUrl)) {
      mPicasso.load(filmDetails.posterUrl).placeholder(R.drawable.people) //temp
          .error(R.drawable.people) //temp
          .fit().into(mPosterImage, new Callback() {
        @Override public void onSuccess() {
          final Bitmap bitmap = ((BitmapDrawable) mPosterImage.getDrawable()).getBitmap();
          Palette.from(bitmap)
              .maximumColorCount(3)
              .clearFilters()
              .generate(new Palette.PaletteAsyncListener() {
                @Override public void onGenerated(Palette palette) {
                  boolean isDark;
                  final int lightness = Colors.isDark(palette);
                  if (lightness == Colors.LIGHTNESS_UNKNOWN) {
                    isDark = Colors.isDark(bitmap, bitmap.getWidth() / 2, 0);
                  } else {
                    isDark = lightness == Colors.IS_DARK;
                  }
                  applyToStatusBar(isDark, palette);
                  applyToToolbar(palette);
                  applyToFab(palette);
                  //applyToBackButton(isDark);
                }
              });
        }

        @Override public void onError() {
        }
      });
    }
  }

  //TODO: make these common somehow ?
 /* private void applyToBackButton(boolean isDark) {
    //if(isDark) {
      final AppCompatActivity activity = (AppCompatActivity) getActivity();
      ColorFilter colorFilter = new PorterDuffColorFilter(ContextCompat.getColor(activity, android.R.color.white),
          PorterDuff.Mode.SRC_ATOP);
      Drawable upArrow = ContextCompat.getDrawable(activity, R.drawable.ic_arrow_back);
      upArrow.setColorFilter(colorFilter);
      if (activity.getSupportActionBar() != null) {
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
      }
    //}
  }*/

  private void applyToFab(Palette palette) {
    Palette.Swatch topColor = palette.getVibrantSwatch();
    if (topColor == null) return;
    mFilmFab.setBackgroundTintList(ColorStateList.valueOf(topColor.getRgb()));
  }

  /**
   * Applies palette to status bar with a light or dark color on M
   * and with status bar icons matching the same color.
   */
  @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void applyToStatusBar(Boolean isDark,
      Palette palette) {
    int statusBarColor = getActivity().getWindow().getStatusBarColor();
    Palette.Swatch topColor = Colors.getMostPopulousSwatch(palette);
    if (topColor != null && (isDark || isAtLeastM())) {
      statusBarColor = Colors.scrimify(topColor.getRgb(), isDark, 0.075f);
      if (!isDark && isAtLeastM()) {
        setLightStatusBar(mPosterImage);
      }
    }

    if (statusBarColor != getActivity().getWindow().getStatusBarColor()) {
      ValueAnimator statusBarColorAnim =
          ValueAnimator.ofArgb(getActivity().getWindow().getStatusBarColor(), statusBarColor);
      statusBarColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override public void onAnimationUpdate(ValueAnimator animation) {
          getActivity().getWindow().setStatusBarColor((Integer) animation.getAnimatedValue());
        }
      });
      statusBarColorAnim.setDuration(1000);
      statusBarColorAnim.setInterpolator(
          AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.fast_out_slow_in));
      statusBarColorAnim.start();
    }
  }

  @TargetApi(Build.VERSION_CODES.M) private void setLightStatusBar(View view) {
    if (isAtLeastM()) {
      int flags = view.getSystemUiVisibility();
      flags = flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      view.setSystemUiVisibility(flags);
    }
  }

  private void applyToToolbar(Palette palette) {
    int primaryDark = ContextCompat.getColor(getActivity(), R.color.primary_dark);
    int primary = ContextCompat.getColor(getActivity(), R.color.primary);
    if (mCollapsingToolbarLayout == null) {
      mToolbar.setBackgroundColor(palette.getMutedColor(primary));
    } else {
      mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
      mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
    }
  }
}

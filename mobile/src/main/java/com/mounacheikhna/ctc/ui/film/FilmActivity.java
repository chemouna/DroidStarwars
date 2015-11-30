package com.mounacheikhna.ctc.ui.film;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.tmdb.FilmDetails;

/**
 * Created by mouna on 28/11/15.
 */
public class FilmActivity extends AppCompatActivity {

  public static final String FILM_EXTRA = "film_extra";
  private FilmDetails mFilm;

  public static Intent getIntent(Context context, FilmDetails resource) {
    Intent intent = new Intent(context, FilmActivity.class);
    intent.putExtra(FILM_EXTRA, resource);
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.film_layout);

    mFilm = getIntent().getParcelableExtra(FILM_EXTRA);
    FilmDetailsFragment fragment = FilmDetailsFragment.newInstance(mFilm);
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.add(R.id.main_fragment, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }
}

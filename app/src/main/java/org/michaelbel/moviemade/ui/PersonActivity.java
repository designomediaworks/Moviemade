package org.michaelbel.moviemade.ui;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.ui.fragment.ListMoviesFragment;
import org.michaelbel.moviemade.ui.fragment.PersonFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

import java.util.Locale;

public class PersonActivity extends BaseActivity {

    private Cast castPerson;
    private People peoplePerson;

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public ViewPager viewPager;
    public TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        castPerson = (Cast) getIntent().getSerializableExtra("cast_person");
        peoplePerson = getIntent().getParcelableExtra("people_person");

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        if (castPerson != null) {
            adapter.addFragment(PersonFragment.newInstance(castPerson), R.string.Info);
            adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_BY_PERSON, castPerson), R.string.Movies);
        } else if (peoplePerson != null) {
            adapter.addFragment(PersonFragment.newInstance(peoplePerson), R.string.Info);
            //adapter.addFragment(ListMoviesFragment.newInstance(ListMoviesFragment.LIST_BY_PERSON, peoplePerson), R.string.Movies);
        }

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.selectedTabColor()));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.unselectedTabColor()), ContextCompat.getColor(this, Theme.selectedTabColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.Share)
            .setIcon(R.drawable.avd_share)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                Drawable icon = menu.getItem(0).getIcon();
                if (icon instanceof Animatable) {
                    ((Animatable) icon).start();
                }

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, Url.TMDB_PERSON, castPerson != null ? castPerson.id : peoplePerson.id));
                startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }
}
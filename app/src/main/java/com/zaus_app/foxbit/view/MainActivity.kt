package com.zaus_app.foxbit.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zaus_app.foxbit.R
import com.zaus_app.foxbit.data.entity.Album
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.ActivityMainBinding
import com.zaus_app.foxbit.view.fragments.NavigationFragment
import com.zaus_app.foxbit.view.fragments.PermissionFragment
import com.zaus_app.foxbit.view.fragments.PlayerFragment
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            changeFragment(PermissionFragment(), "permission")
        } else {
            changeFragment(NavigationFragment(), "navigation")
        }
    }

    fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .commit()
    }

    fun getAllSongs(): ArrayList<Song> {
        val result = ArrayList<Song>()
        val selection =
            MediaStore.Audio.Media.IS_MUSIC + "!=0" // + " AND " + MediaStore.Audio.Media.IS_RECORDING + "==0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC", null
        )
        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val file = File(path)
                    if (file.exists()) {
                        val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                        val title =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                        val artist =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                        val album =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                        val duration =
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                        val song = Song(id, title, album, artist, duration, path)
                        result.add(song)
                    }
                } while (cursor.moveToNext())
            cursor.close()
        }
        return result
    }

    fun getAllAlbums(): ArrayList<Album> {
        val result = ArrayList<Album>()
        val selection =
            MediaStore.Audio.Media.IS_MUSIC + "!=0"// + " AND " + MediaStore.Audio.Media.IS_RECORDING + "==0"
        val projection = arrayOf(
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
        )
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            MediaStore.Audio.Media.ALBUM + " ASC",
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    val album_id =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID))
                    val title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
                    val artist =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST))
                    val album = Album(album_id, title, artist)
                    if (result.isEmpty())
                        result.add(album)
                    else
                        for (i in result.indices) {
                            if (result[i].title != album.title) {
                                result.add(album)
                                break
                            }
                        }
                } while (cursor.moveToNext())
            cursor.close()
        }
        return result
    }

    fun launchPlayerFragment(position: Int) {
        val playerFragment =  ((checkFragmentExistence("player") ?: PlayerFragment()) as BottomSheetDialogFragment)
        val bundle = Bundle()
        bundle.putInt("position", position)
        playerFragment.arguments = bundle
        playerFragment.show(supportFragmentManager, "player")
    }

    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)


    override fun onSupportNavigateUp(): Boolean {
        val navigationFragment: NavigationFragment =
            supportFragmentManager.findFragmentByTag("navigation") as NavigationFragment
        return navigationFragment.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("navigation")
        if (fragment != null) {
            val navigationFragment = fragment as NavigationFragment
            val drawerLayout = navigationFragment.getDrawer()
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}
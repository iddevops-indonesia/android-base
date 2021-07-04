package com.iddevops.android_base.utils.bases.presentation

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Base class for activity
 * @sample com.dev.sample.presentation.home.HomeActivity
 * @constructor instance for activity class
 * @property binding the layout binding
 * @property menuId the menu id for the toolbar, null by default
 * @property menuListener the listener for the item menu, null by default
 * @property onPermissionCallBack callBack for permission request purposes
 */

abstract class BaseActivity<T: ViewBinding> : AppCompatActivity(), BaseView {
    private var menuId: Int? = null
    open lateinit var binding: T
    private var menuListener: ((Int) -> Boolean)? = null
    private var onPermissionCallBack: Triple<Int, (() -> Unit)?, (() -> Unit)?>? = null

    /**
     * This method declare here to set content view from layout
     * @param savedInstanceState saved bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getLayoutBinding()
        setContentView(binding.root)
        onViewReady()
    }

    /**
     * Method to inflate the layout binding
     * @return the layout binding
     */
    protected abstract fun getLayoutBinding(): T

    /**
     * Series of actions when the fragment is ready
     */
    private fun onViewReady() {
        initData()
        initUI()
        initAction()
        initObserver()
    }

    /**
     * Method to init global variable or data from intent
     */
    abstract fun initData()

    /**
     * Method for UI configuration and initialization
     */
    abstract fun initUI()

    /**
     * Method to action to do in activity
     */
    abstract fun initAction()

    /**
     * Method to initialize observer
     */
    abstract fun initObserver()

    /**
     * Method to set activity's toolbar
     * @param toolbar Toolbar that defined in XML layout, nullable
     * @param title Title for toolbar, nullable
     * @param isChild Display back button it toolbar?
     * @param menu menu Id, nullable if not needed
     * @param onMenuListener listener when item of the menu selected, nullable if not needed
     */
    override fun setupToolbar(
        toolbar: Toolbar?,
        title: String?,
        isChild: Boolean,
        menu: Int?,
        onMenuListener: ((Int) -> Boolean)?
    ) {
        menuId = menu
        menuListener = onMenuListener
        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.let { tb ->
                title?.let { title -> tb.title = title }
                tb.setDisplayHomeAsUpEnabled(isChild)
                invalidateOptionsMenu()
            }
        } ?: run {
            supportActionBar?.let {
                it.title = title
                it.setDisplayHomeAsUpEnabled(isChild)
                invalidateOptionsMenu()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuId?.let { menuInflater.inflate(it, menu) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuListener?.invoke(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    /**
     * Method to request permission to user
     * @param permissions list of permissions to request
     * @param requestCode requestCode for request process, can be used later
     * @param onPermissionGranted action to do when permission granted
     * @param onPermissionNotGranted action to do when permission not granted
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(
        permissions: Array<String>,
        requestCode: Int,
        onPermissionGranted: (() -> Unit)? = null,
        onPermissionNotGranted: (() -> Unit)? = null
    ) {
        onPermissionCallBack = Triple(requestCode, onPermissionGranted, onPermissionNotGranted)

        val notGrantedPermission = arrayListOf<String>().apply {
            permissions.forEach {
                if (!hasPermission(it))
                    this.add(it)
            }
        }
        requestPermissions(notGrantedPermission.toTypedArray(), requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            onPermissionCallBack?.first -> {
                if (grantResults.isNotEmpty() && grantResults.sum() == PackageManager.PERMISSION_GRANTED)
                    onPermissionCallBack?.second?.invoke()
                else
                    onPermissionCallBack?.third?.invoke()
            }
            else -> {

            }
        }
    }

    /**
     * Method to check if application has permission
     * @param permission name of the permission to check
     * @return is the application has the permission?
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String) =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    /**
     * Method to check if the application has permissions
     * @param permissions array of the permission to check
     * @return is the application has the permissions?
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermissions(permissions: Array<String>): Boolean {
        var result = true
        permissions.forEach {
            if (!hasPermission(it)) {
                result = false
                return@forEach
            }
        }
        return result
    }

    /**
     * Method to attach fragment to layout
     * @param viewRes id of Fragment/ FrameLayout
     * @param fragment fragment to attached
     * @param addToBackStack add the fragment to backStack of application
     */
    fun setFragment(viewRes: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(viewRes, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    /**
     * Method to start picking image from gallery
     * @param requestCode request code for Id
     */
    fun startPickImageFromGallery(requestCode: Int) {
        startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), requestCode
        )
    }

    /**
     * Method to start picking image from camera
     * @param requestCode request code for Id
     */
    fun startPickImageFromCamera(requestCode: Int) {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), requestCode)
    }

    /**
     * Method to finish current activity
     */
    override fun finishActivity() {
        finish()
    }
}
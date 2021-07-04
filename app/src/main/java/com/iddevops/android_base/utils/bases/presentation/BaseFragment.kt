package com.iddevops.android_base.utils.bases.presentation

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding>: Fragment(), BaseView {
    open lateinit var binding: T
    protected var currentActivity: BaseActivity<*>? = null
    private var onPermissionCallBack: Triple<Int, (() -> Unit)?, (() -> Unit)?>? = null

    /**
     * onCreate method, here define Fragment has no options menu.
     * If you need options menu, override this method and setHasOptionsMenu(true)
     * @param savedInstanceState instance of state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    /**
     * Method to inflate fragment's view
     * @param inflater Layout Inflater
     * @param container ViewGroup
     * @param savedInstanceState savedInstance
     * @return View that will be used in fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getLayoutBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady()
    }

    /**
     * Method to inflate Layout Binding
     * @return the binding of layout
     */
    protected abstract fun getLayoutBinding(): T

    /**
     * Method to set currentActivity's value and call attachListener
     * @param context and here is the attached activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            currentActivity = context
        }
    }

    /**
     * Method to notify activity that fragment detached
     */
    override fun onDetach() {
        currentActivity = null
        super.onDetach()
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
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || currentActivity?.checkSelfPermission(
            permission
        ) == PackageManager.PERMISSION_GRANTED

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
     * Method to set activity's toolbar from fragment
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
        currentActivity?.setupToolbar(toolbar, title, isChild, menu, onMenuListener)
    }

    /**
     * Method to finish activity where the fragment attached
     */
    override fun finishActivity() {
        currentActivity?.finishActivity()
    }

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
     * Method to action to do in fragment
     */
    abstract fun initAction()

    /**
     * Method to initialize observer
     */
    abstract fun initObserver()

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
}
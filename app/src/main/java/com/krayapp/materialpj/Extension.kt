package com.krayapp.materialpj

import androidx.fragment.app.Fragment

class Extension {

}
fun Fragment.Toast(text:String){
    android.widget.Toast.makeText(context, "$text", android.widget.Toast.LENGTH_SHORT).show()
}
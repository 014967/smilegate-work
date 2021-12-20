package com.example.layoutpractice.ui.ManagerActivity

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.layoutpractice.R
import com.example.layoutpractice.model.User

class ManagerActivityAdapter(
    context: Context?,
    userArrayList: ArrayList<User>?,
    callback: AdapterCallback
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {


    interface AdapterCallback {
        fun onItemClicked(user: ArrayList<User>)
    }


    var context: Context? = context
    private var users: ArrayList<User>? = userArrayList

    //var checkedUser : HashMap<String, String>? = HashMap()
    private var checkedUser: ArrayList<User>? = userArrayList
    var adapterCallback: AdapterCallback? = callback

    val TAG: String = "로그"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //TODO("Not yet implemented")
        val rootView: View =
            LayoutInflater.from(context).inflate(R.layout.item_recylcerview, parent, false)
        return RecyclerViewViewHolder(rootView)


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO("Not yet implemented")

        val user = users?.get(position)
        if (user != null) {
            val viewHolder = holder as RecyclerViewViewHolder
            var tokenString: String? = ""
            if (user?.roles?.size != null) {
                for (i in 0 until user?.roles!!.size) {
                    tokenString = tokenString + user!!.roles!![i].name + " "

                }
            }

            viewHolder.tv_email.text = user?.email
            viewHolder.tv_name.text = user?.name
            viewHolder.tv_roles.text = tokenString

            viewHolder.cb_role.setOnCheckedChangeListener(CheckboxListener(user!!))
        }


    }


    override fun getItemCount(): Int {
        if (users == null) {
            return 0
        } else {
            return users?.size!!
        }

    }

    internal inner class RecyclerViewViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tv_name: TextView
        var tv_email: TextView
        var tv_roles: TextView
        var cb_role: CheckBox

        init {
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_email = itemView.findViewById(R.id.tv_email)
            tv_roles = itemView.findViewById(R.id.tv_roles)
            cb_role = itemView.findViewById(R.id.cb_role)
        }


    }

    inner class CheckboxListener(var user: User) : CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(view: CompoundButton?, isChecked: Boolean) {
            when (view?.id) {
                R.id.cb_role -> {
                    if (isChecked) {
                        checkedUser?.add(user)
                        checkedUser?.let { adapterCallback?.onItemClicked(it) }
                    }


                }

            }
        }
    }
}



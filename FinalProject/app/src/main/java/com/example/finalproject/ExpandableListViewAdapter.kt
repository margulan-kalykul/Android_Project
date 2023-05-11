package com.example.finalproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class ExpandableListViewAdapter internal constructor(private val context: Context, private val chapterList: List<String>, private val topicsList: HashMap<String, List<String>>):
    BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return chapterList.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return this.topicsList[this.chapterList[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return chapterList[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return this.topicsList[this.chapterList[p0]]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var view = p2
        val chapterTitle = getGroup(p0) as String

        if(view == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.chapter_list, null)
        }
        val chapterTv = view!!.findViewById<TextView>(R.id.chapter_tv)

        chapterTv.text = chapterTitle
        return view
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View? {
//        var p3 = p3
//        val topicTitle = getChild(p0, p1) as String
//
//        if(p3 == null){
//
//            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            p3 = inflater.inflate(R.layout.topics_list, null)
//        }
//        val topicTv = p3!!.findViewById<TextView>(R.id.chapter_tv)
//
//        topicTv.setText(topicTitle)
//        return p3
        val topicTitle = getChild(p0, p1) as String
//        Log.d("topic", topicTitle)
        var view = p3
        if (view == null) {

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.topics_list, null)
        }

        val topicTv = view?.findViewById<TextView>(R.id.topics_tv)
        if (topicTv == null) {
            Log.d("topic", "hello world")
            return view
        }

        topicTv.text = topicTitle
        Log.d("topicTv", topicTv.text.toString())
//        topicTv.setText(topicTitle)

        return view
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

}
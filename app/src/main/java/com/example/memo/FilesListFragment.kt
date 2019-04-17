package com.example.memo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.File

class FilesListFragment : Fragment() {

    //メモをタップした時のコールバックインターフェイス
    interface OnFileSelectListener {
        fun onFileSelected(file: File)
    }

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        //アクティビティがコールバックを実装していなかったら、例外を投げる
        if (context !is OnFileSelectListener)
            throw RuntimeException("$context must implement OnFileSelectListener")
    }

    fun show() {
        // contextのnullチェック
        val ctx = context ?: return
        // ファイルの一覧を表示するためのアダプター
        val adapter = FilesAdapter(ctx, getFiles()) { file ->
            //タップされたら、コールバックを呼ぶ
            (context as OnFileSelectListener).onFileSelected(file)
        }
        recyclerView.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.filesList)
        //縦方向に並べてリストを表示するレイアウトマネージャーを設定
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        show()
        return view
    }

}
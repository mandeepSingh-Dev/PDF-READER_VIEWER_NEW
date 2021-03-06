package com.product.pdf_reader_viewer.RecylerViewClasses

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.product.pdf_reader_viewer.PdfView_Activity
import com.example.pdf_reader_viewer.R
import com.product.pdf_reader_viewer.UtilClasses.PDFProp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyAdapter_ForMerge(context:Context,arrayList:ArrayList<Items_pdfs>):RecyclerView.Adapter<MyAdapter_ForMerge.MyViewholderMerge>()
{
    var contextt=context
    var pdflist:ArrayList<Items_pdfs> = arrayList
    var mCustomOnClickListener: com.product.pdf_reader_viewer.MCustomOnClickListener?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholderMerge {
        val view=LayoutInflater.from(contextt).inflate(R.layout.list_merge_item,parent,false)

        val myViewholderMerge=MyViewholderMerge(view)
        return myViewholderMerge
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: MyViewholderMerge, position: Int) {

        Log.d("sizeeifhigv",pdflist.size.toString())

        val itemPdf=pdflist.get(position)
            holder.pdfName?.setText(itemPdf.title)
      //  holder.pdfSize?.setText(itemPdf?.size)
       // holder.pdfDate?.setText(itemPdf?.date_modified)

        holder.itemView.setOnClickListener {
            contextt.startActivity(
                Intent(contextt, PdfView_Activity::class.java)
                .setAction(PDFProp.MY_OPEN_ACTION)
                .putExtra(PDFProp.PDF_APPENDED_URI,itemPdf.appendeduri.toString())
                .putExtra(PDFProp.PDF_TITLE,itemPdf.title)
                .putExtra(PDFProp.PDF_SIZE,"df"))

            Log.d("wifhwedf",itemPdf.appendeduri.toString())

            CoroutineScope(Dispatchers.Main).launch {
              //  insertToRecentDATABASE(title!!,size!!,appendeduri?.toString()!!,System.currentTimeMillis())
            }
        }

      //deleting the merge selected pdfs
        holder.dragmenuimage?.setOnClickListener {
           // mCustomOnClickListener?.onClick(position)
            val popupmenu = PopupMenu(contextt,holder.dragmenuimage)
            popupmenu.getMenuInflater().inflate(R.menu.deletemenu, popupmenu.getMenu())
            popupmenu.setOnMenuItemClickListener(object:PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when(item?.itemId)
                    {
                        R.id.deleteMenu -> {
                            pdflist.removeAt(position)
                             notifyDataSetChanged()
                            notifyItemRemoved(position)
                            return  true
                        }
                        else -> {
                            return true
                        }
                    }
                }
            })
            popupmenu.show()
        }


    }

    override fun getItemCount(): Int {
        return  pdflist.size
    }

    class MyViewholderMerge(itemView: View):RecyclerView.ViewHolder(itemView) {

       var pdfName = itemView.findViewById<TextView>(R.id.pdfNameTextView)
        var pdfSize = itemView.findViewById<TextView>(R.id.pdfSize)
        var pdfDate = itemView.findViewById<TextView>(R.id.pdfDateTexView)
        var dragmenuimage = itemView.findViewById<ImageView>(R.id.dragMenuimage)


    }
    fun setMCustomClickListenr(mCustomOnClickListener: com.product.pdf_reader_viewer.MCustomOnClickListener)
    {
        this.mCustomOnClickListener=mCustomOnClickListener
    }
}
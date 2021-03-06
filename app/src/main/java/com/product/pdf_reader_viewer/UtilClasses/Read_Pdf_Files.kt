package com.product.pdf_reader_viewer.UtilClasses

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import com.product.pdf_reader_viewer.RecylerViewClasses.Items_pdfs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// pdfs Repositry (Means that this class is for getting pdflist from local device)
class Read_Pdf_Files(context:Context)
{
    var context=context
    var pdflist:ArrayList<Items_pdfs>?=null


    @SuppressLint("Range")
    suspend fun getPdfList_2():ArrayList<Items_pdfs> = withContext(Dispatchers.IO) {

        pdflist=ArrayList()
        // progressBar?.visibility=View.VISIBLE
        /**THIS URI IS WORKING FOR ALL ANDROID VERSIONS*/
       var externalUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
      //  MediaStore.Files.getContentUri(MediaStore.VOLUME_INTERNAL)
        /**sql-where-clause-with-placeholder-variables  Here we select MimeType*/
        val selection = MediaStore.Files.FileColumns.MIME_TYPE + " = ?"
        /**getting MIME type for pdf*/
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
        /**values-of-placeholder-variables  giving mimetype to selection_args */
        val selectionArgs = arrayOf(mimeType)
        /**content Resolver to get cursor for filess..*/

        var contentResolver = context?.contentResolver
        var cursor: Cursor? = null

        /**media-database-columns-to-retrieve*/
        var stringProjectioon:Array<String>? =null

        /**list for buckets or folders*/
        var bucketsList = ArrayList<String>()

        /**getting list according to android build versions */
         if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
            stringProjectioon = arrayOf(
                MediaStore.Files.FileColumns.DISPLAY_NAME,       //this column shows null below android 10
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME, //For folder/bucket name of item/pdf
                MediaStore.Files.FileColumns.MIME_TYPE,           //for selecting only MIME type
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.RELATIVE_PATH
            )
            cursor = contentResolver?.query(externalUri!!, stringProjectioon, selection, selectionArgs, null)
            /**below line is to get files from particular bucket or folder*/
            //   var cursor = contentResolver?.query(externalUri!!, stringProjectioon,  MediaStore.Images.Media._ID + " like ? ", arrayOf("%Download%"), null, null)
             /**getting coloumns name*/
            var titleColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.TITLE)!!
            val idColoumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val displayColoumn =
                cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val bucketColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)!!
            val dateModColoumn = cursor?.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED)!!
            val sizeColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.SIZE)!!
            val relativePathColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.RELATIVE_PATH)!!

            /**Getting all cursors in loop*/
            while (cursor?.moveToNext()!!) {
                var displeynamee = cursor?.getString(displayColoumn)
                var title = cursor?.getString(titleColoumn)
                var id = cursor?.getLong(idColoumn)
                var bucket = cursor?.getString(bucketColoumn)
                var dateModified = cursor?.getString(dateModColoumn)
                var size = cursor?.getString(sizeColoumn)
                var relativepath = cursor?.getString(relativePathColoumn)
                Log.d("fooehuifhned",title+id+bucket+dateModified+size+relativepath)

                //Handling Nullability for varables
                if (bucket != null) { bucketsList.add(bucket!!) }
                if(title==null){ title="N.A"}
                if(displeynamee==null){displeynamee="N.A"}
                if(dateModified==null){dateModified="N.A"}
                if(size==null){size="N.A"}
                if(relativepath==null){relativepath="N.A"}
                if(bucket==null){bucket="N.A"}

                /** As Data column id deprecated so we appended id with VOLUME_EXTERNAL*/
                var data_uri = Uri.withAppendedPath(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), id.toString())
                //  pdflist?.add(Items_pdfs(title!!, size!!, data_uri, dateModified, relativepath!!, bucket))
                /**adding cursoritems to pdflist in loop */
                pdflist?.add(Items_pdfs(displeynamee!!, size!!, data_uri, dateModified, relativepath!!, bucket))
            }
            cursor.close()
            Log.d("33fffffffwf",pdflist?.size.toString()+"fed8fhef")
            return@withContext pdflist!!
        } //END OF IF BLOCK
        else{
            stringProjectioon= arrayOf(
                MediaStore.Files.FileColumns.DISPLAY_NAME,       //this column shows null below and equal to android 10
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME, //For folder/bucket name of item/pdf
                MediaStore.Files.FileColumns.MIME_TYPE,           //for selecting only MIME type
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.SIZE )

            /**Query cursors that include files or data*/

            /**Query cursors that include files or data*/
            cursor = contentResolver?.query(externalUri!!, stringProjectioon, selection, selectionArgs, null)
            /**below line is to get files from particular bucket or folder*/
            /**below line is to get files from particular bucket or folder*/
            //   var cursor = contentResolver?.query(externalUri!!, stringProjectioon,  MediaStore.Images.Media._ID + " like ? ", arrayOf("%Download%"), null, null)

            /**getting coloumns name*/
            /**getting coloumns name*/
            var titleColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.TITLE)!!
            val idColoumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)!!
            val displayColoumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)!!
            val bucketColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)!!
            val dateModColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)!!
            val sizeColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.SIZE)!!
            val relativePathColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.RELATIVE_PATH)!!


            /**Getting all cursors in loop*/
            while (cursor?.moveToNext()!!) {

                // Log.d("dfdfd",cursor.toString())
                var title = cursor?.getString(titleColoumn)
                var id = cursor?.getLong(idColoumn)
                var bucket = cursor?.getString(bucketColoumn)
                var dateModified = cursor?.getString(dateModColoumn)
                var size = cursor?.getString(sizeColoumn)



                //Handling Nullability for varables
                if (bucket != null) { bucketsList.add(bucket!!) }
                if (bucket != null) { bucketsList.add(bucket!!) }
                if(title==null){title="N.A"}
                if(dateModified==null){dateModified="N.A"}
                if(size==null){size="N.A"}
                if(bucket==null){bucket="N.A"}
                Log.d("PPPDFDF",  " " + title + "____"/*+RELATIVEPATH*/ + id +"_____"+bucket)
                // activity?.runOnUiThread {
                //   Toast.makeText(context,/*RELATIVEPATH+*/"  " + title + volumename, Toast.LENGTH_LONG).show()
                //  }
                /** As Data column id deprecated so we appended id with VOLUME_EXTERNAL*/
                /** As Data column id deprecated so we appended id with VOLUME_EXTERNAL*/
                var data_uri = Uri.withAppendedPath(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), id.toString())

                //  pdflist?.add(Items_pdfs(title!!, size!!, data_uri, dateModified, relativepath!!, bucket))
                /**adding cursoritems to pdflist in loop */
                /**adding cursoritems to pdflist in loop */

                pdflist?.add(Items_pdfs(title!!, size!!, data_uri, dateModified, bucket))
            }
            cursor.close()

            return@withContext pdflist!!
        } //END OF ELSE BLOCK


        //    Log.d("3oujr74ghvn",pdflist?.size.toString())
        /**Method for Removing duplicate bucket/folder names from folderList*/
        /**Method for Removing duplicate bucket/folder names from folderList*/
        // filtering_FolderArraylist(folderlistExm)


        //  LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent("SENDINGFOLDERLIST").putStringArrayListExtra("FOLDERLIST",folderlistExm))

    }

    @SuppressLint("Range")
    suspend fun getPdfList_Folder(folderName:String):ArrayList<Items_pdfs> = withContext(Dispatchers.Default)
    {

        pdflist=ArrayList()
        // progressBar?.visibility=View.VISIBLE


        /**THIS URI IS WORKING FOR ALL ANDROID VERSIONS*/
        var externalUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        //  MediaStore.Files.getContentUri(MediaStore.VOLUME_INTERNAL)

        /**sql-where-clause-with-placeholder-variables  Here we select MimeType*/
        val selection = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + " like ? "


        /**getting MIME type for pdf*/
      //  val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")

        /**values-of-placeholder-variables  giving mimetype to selection_args */
        //val selectionArgs = arrayOf("%Download%")
        val selectionArgs = arrayOf(folderName)

        /**content Resolver to get cursor for filess..*/
        /**content Resolver to get cursor for filess..*/
        var contentResolver = context?.contentResolver
        var cursor: Cursor? = null

        /**media-database-columns-to-retrieve*/
        /**media-database-columns-to-retrieve*/
        var stringProjectioon:Array<String>? =null

        /**list for buckets or folders*/
        /**list for buckets or folders*/
        var bucketsList = ArrayList<String>()


        /**getting list according to android build versions */
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R) {
            stringProjectioon = arrayOf(
                MediaStore.Files.FileColumns.DISPLAY_NAME,       //this column shows null below android 10
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME, //For folder/bucket name of item/pdf
                MediaStore.Files.FileColumns.MIME_TYPE,           //for selecting only MIME type
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.RELATIVE_PATH
            )

            cursor = contentResolver?.query(externalUri!!, stringProjectioon,selection, selectionArgs, null)

            /**below line is to get files from particular bucket or folder*/

            /**below line is to get files from particular bucket or folder*/
              // var cursor = contentResolver?.query(externalUri!!, stringProjectioon,  MediaStore.Images.Media._ID + " like ? ", arrayOf("%Download%"), null, null)

            /**getting coloumns name*/
            /**getting coloumns name*/
            var titleColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.TITLE)!!
            val idColoumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)!!
            val displayColoumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)!!
            val bucketColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)!!
            val dateModColoumn = cursor?.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED)!!
            val sizeColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.SIZE)!!
            val relativePathColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.RELATIVE_PATH)!!
            val mimetypeColoumn = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
            /**Getting all cursors in loop*/

           // Log.d("39gh4jg",cursor?.count.toString())
            /**Getting all cursors in loop*/
            while (cursor?.moveToNext()!!) {

                // Log.d("dfdfd",cursor.toString())
                var displeynamee = cursor?.getString(displayColoumn)
                var title = cursor?.getString(titleColoumn)
                var id = cursor?.getLong(idColoumn)
                var bucket = cursor?.getString(bucketColoumn)
                var dateModified = cursor?.getString(dateModColoumn)
                var size = cursor?.getString(sizeColoumn)
                var relativepath = cursor?.getString(relativePathColoumn)
                var mimetypee= cursor?.getString(mimetypeColoumn)
                Log.d("8785yt85gh58",title+id+bucket+dateModified+size+relativepath)

               /* if(mimetypee!=null)
                Log.d("4g9h5johmn45",mimetypee)

                if(mimetypee.equals("application/pdf"))
                {
                    Log.d("38yh384g4",title+"k")
                }else{Log.d("38yh39899999999984g4","titilenull")}*/

                //practise
                Log.d("3ggh3gh3vb",dateModified.toString()+"gfgege")

                //Handling Nullability for varables
                if (bucket != null) { bucketsList.add(bucket!!) }
                if(title==null){title="N.A"}
                if(dateModified==null){dateModified="N.A"}
                if(size==null){size="N.A"}
                if(relativepath==null){relativepath="N.A"}
                if(bucket==null){bucket="N.A"}



                // Log.d("PPPDFDF", displeynamee + " " + title + "____"/*+RELATIVEPATH*/ + id +"_____"+bucket)
                // activity?.runOnUiThread {
                //   Toast.makeText(context,/*RELATIVEPATH+*/"  " + title + volumename, Toast.LENGTH_LONG).show()
                //  }
                /** As Data column id deprecated so we appended id with VOLUME_EXTERNAL*/
                /** As Data column id deprecated so we appended id with VOLUME_EXTERNAL*/
                var data_uri = Uri.withAppendedPath(
                    MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    id.toString()
                )
                //  pdflist?.add(Items_pdfs(title!!, size!!, data_uri, dateModified, relativepath!!, bucket))
                /**adding cursoritems to pdflist in loop */
                /**adding cursoritems to pdflist in loop */
                if(mimetypee.equals("application/pdf")) {
                    pdflist?.add(Items_pdfs(title!!, size!!, data_uri, dateModified, relativepath!!, bucket))
                     }
                }
            cursor.close()
            Log.d("folderITEMS_SIZE",pdflist?.size.toString()+"fed8fhef")
            return@withContext pdflist!!
        } //END OF IF BLOCK
        else{
            stringProjectioon= arrayOf(
                MediaStore.Files.FileColumns.DISPLAY_NAME,       //this column shows null below and equal to android 10
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME, //For folder/bucket name of item/pdf
                MediaStore.Files.FileColumns.MIME_TYPE,           //for selecting only MIME type
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.SIZE )

            /**Query cursors that include files or data*/

            /**Query cursors that include files or data*/
            cursor = contentResolver?.query(externalUri!!, stringProjectioon, selection, selectionArgs, null)
            /**below line is to get files from particular bucket or folder*/
            /**below line is to get files from particular bucket or folder*/
            //   var cursor = contentResolver?.query(externalUri!!, stringProjectioon,  MediaStore.Images.Media._ID + " like ? ", arrayOf("%Download%"), null, null)

            /**getting coloumns name*/
            /**getting coloumns name*/
            var titleColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.TITLE)!!
            val idColoumn = cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)!!
            val bucketColoumn =
                cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
            val displayColoumn= cursor?.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dateModColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val sizeColoumn = cursor?.getColumnIndex(MediaStore.Files.FileColumns.SIZE)
            val relativePathColoumn =
                cursor?.getColumnIndex(MediaStore.Files.FileColumns.RELATIVE_PATH)


            /**Getting all cursors in loop*/
          //  if(cursor!=null) {
                while (cursor?.moveToNext()) {

                    // Log.d("dfdfd",cursor.toString())
                    var title = cursor?.getString(titleColoumn)
                    var id = cursor?.getLong(idColoumn)
                    var bucket = cursor?.getString(bucketColoumn)
                    var dateModified = cursor?.getString(dateModColoumn)
                    var size = cursor?.getString(sizeColoumn)


                    //Handling Nullability for varables
                    if (bucket != null) { bucketsList.add(bucket) }
                    if (bucket != null) { bucketsList.add(bucket) }
                    if (title == null) { title = "N.A" }
                    if (dateModified == null) { dateModified = "N.A" }
                    if (size == null) { size = "N.A" }
                    if (bucket == null) { bucket = "N.A" }
                    Log.d("PPPDGGHFDF", " " + title + "____"/*+RELATIVEPATH*/ + id + "_____" + bucket)
                    // activity?.runOnUiThread {
                    //   Toast.makeText(context,/*RELATIVEPATH+*/"  " + title + volumename, Toast.LENGTH_LONG).show()
                    //  }
                    /** As Data column id deprecated so we appended id with VOLUME_EXTERNAL*/
                    /** As Data column id deprecated so we appended id with VOLUME_EXTERNAL*/
                    var data_uri = Uri.withAppendedPath(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), id.toString())
//                     Log.d("84YG8HG",data_uri.authority.toString())
//                    Log.d("3489g4jhg",File(data_uri.toString()).name)

                    //  pdflist?.add(Items_pdfs(title!!, size!!, data_uri, dateModified, relativepath!!, bucket))
                    /**adding cursoritems to pdflist in loop */
                    /**adding cursoritems to pdflist in loop */

                    pdflist?.add(Items_pdfs(title, size, data_uri, dateModified, bucket))
                }
          //  }//if block for cursor nullablity
            cursor.close()

            return@withContext pdflist!!
        } //END OF ELSE BLOCK


        //    Log.d("3oujr74ghvn",pdflist?.size.toString())
        /**Method for Removing duplicate bucket/folder names from folderList*/
        /**Method for Removing duplicate bucket/folder names from folderList*/
        // filtering_FolderArraylist(folderlistExm)


        //  LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent("SENDINGFOLDERLIST").putStringArrayListExtra("FOLDERLIST",folderlistExm))

    }




}
package com.leadwinner.apicall.FragmentsImages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.leadwinner.apicall.R;

import java.util.ArrayList;

public class ImageListView extends AppCompatActivity {
    GridView imagesList;
//    private String [] imageurls={"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTH5AINNITJeb6juUL9KEzDyqcrNwARUUiW_g&usqp=CAU",
//            "https://static0.cbrimages.com/wordpress/wp-content/uploads/2017/03/kit-harrington-jon-snow-game-of-thrones.jpg",
//            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2tuak9EDKRFk_ylad9ReVZci1idcpZLTFAg&usqp=CAU",
//            "https://static1.colliderimages.com/wordpress/wp-content/uploads/2021/03/justice-league-poster-cast-unite-the-six-social-featured.jpg"};
    ArrayList<String> arrayList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list_view);
        imagesList=findViewById(R.id.imagesList);
        arrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTH5AINNITJeb6juUL9KEzDyqcrNwARUUiW_g&usqp=CAU");
        arrayList.add("https://static0.cbrimages.com/wordpress/wp-content/uploads/2017/03/kit-harrington-jon-snow-game-of-thrones.jpg");
        arrayList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT2tuak9EDKRFk_ylad9ReVZci1idcpZLTFAg&usqp=CAU");
        arrayList.add("https://static1.colliderimages.com/wordpress/wp-content/uploads/2021/03/justice-league-poster-cast-unite-the-six-social-featured.jpg");
        arrayList.add("https://storage-asset.msi.com/event/2020/nb/Intel-Marvels-Avengers-Gaming-Bundle/images/kv-new1.png");
        arrayList.add("https://1.bp.blogspot.com/-aLjmt0B7xAs/XMmyWG2f6wI/AAAAAAAACGI/WxegwUIyT4IowcEcL6R9RwPoRM8JJVvHwCKgBGAs/w0/deadpool-vs-thanos-uhdpaper.com-4K-119.jpg");
        ImageAdapter imageAdapter=new ImageAdapter(this,arrayList);
        imagesList.setAdapter(imageAdapter);
    }
}
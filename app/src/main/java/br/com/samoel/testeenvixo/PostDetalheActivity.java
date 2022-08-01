package br.com.samoel.testeenvixo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.samoel.testeenvixo.adapter.RecycleViewAdapterPost;
import br.com.samoel.testeenvixo.model.Post;

public class PostDetalheActivity extends AppCompatActivity {

    private TextView titulo;
    private ImageView img ;
    private TextView detalhe;
    private AppCompatButton btn_site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detalhe);
        Intent intent = getIntent();

        titulo = findViewById(R.id.txt_titulo_curso_item);
        img = findViewById(R.id.img_curso_item);
        detalhe = findViewById(R.id.txt_descricao_curso_item);

        Post post = new Post();
        post = (Post) intent.getSerializableExtra("post");
        titulo.setText(post.getTitulo());
        carregaIMG(post.getImg(),img);
        detalhe.setText(post.getDescricao());
        btn_site = findViewById(R.id.btn_site);

        btn_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://coursify.me/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });



    }

    private void carregaIMG(String img, ImageView viewHolder) {
        String URL = "https://blog.coursify.me/wp-json/wp/v2/media/"+img;
        RequestQueue queue = Volley.newRequestQueue(PostDetalheActivity.this);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        try {


                            String  img = jsonObj.getString("source_url");



                            Log.i("foii_8","ddd");

                            Picasso.with(PostDetalheActivity.this).load(img).into(viewHolder);

                        } catch (JSONException e) {
                            Log.i("erro_8",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        queue.add(jsonObjectRequest);

    }

}
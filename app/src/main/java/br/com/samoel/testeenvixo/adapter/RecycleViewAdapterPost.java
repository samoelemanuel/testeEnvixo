package br.com.samoel.testeenvixo.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import br.com.samoel.testeenvixo.PostDetalheActivity;
import br.com.samoel.testeenvixo.R;
import br.com.samoel.testeenvixo.model.Post;

public class RecycleViewAdapterPost extends RecyclerView.Adapter<RecycleViewAdapterPost.ViewHolder> {

    private Context context;
    private List<Post> postList;
    private int id;
    private int item;

    public RecycleViewAdapterPost(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso_lista,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tituloCurso.setText(postList.get(position).getTitulo());
        holder.leiaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetalheActivity.class);
                intent.putExtra("post", postList.get(position));
                context.startActivity(intent);
                Log.i("clique",postList.get(position).getTitulo()+"  clicado");
            }
        });
        holder.descricaoCurso.setText(postList.get(position).getDescricao());
        carregaIMG(postList.get(position).getImg(),holder);

    }

    private void carregaIMG(String img,ViewHolder viewHolder) {
        String URL = "https://blog.coursify.me/wp-json/wp/v2/media/"+img;
        RequestQueue queue = Volley.newRequestQueue(context);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObj) {
                        try {


                                String  img = jsonObj.getString("source_url");



                                Log.i("foii_8","ddd");

                            Picasso.with(context).load(img).into(viewHolder.imgCurso);

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


    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCurso;
        TextView tituloCurso;
        TextView descricaoCurso;
        TextView leiaMais;

        public ViewHolder( View itemView) {
            super(itemView);
            this.imgCurso = itemView.findViewById(R.id.img_curso_item);
            this.tituloCurso = itemView.findViewById(R.id.txt_titulo_curso_item);
            this.descricaoCurso = itemView.findViewById(R.id.txt_descricao_curso_item);
            this.leiaMais = itemView.findViewById(R.id.txt_leia_mais_item);



        }
    }
}

package br.com.samoel.testeenvixo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.samoel.testeenvixo.Constantes;
import br.com.samoel.testeenvixo.HomeActivity;
import br.com.samoel.testeenvixo.PostDetalheActivity;
import br.com.samoel.testeenvixo.R;
import br.com.samoel.testeenvixo.compare.ComparePost;
import br.com.samoel.testeenvixo.model.Categoria;
import br.com.samoel.testeenvixo.model.Post;

public class RecycleViewAdapterCategori  extends RecyclerView.Adapter<RecycleViewAdapterCategori.ViewHolder>  {

    private Context context;
    private List<Categoria> categoriaList;
    private int filtro;

    public RecycleViewAdapterCategori(Context context, List<Categoria> categoriaList, int filtro) {
        this.context = context;
        this.categoriaList = categoriaList;
        this.filtro = filtro;
    }

    @NonNull
    @Override
    public RecycleViewAdapterCategori.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria,parent,false);


        return new RecycleViewAdapterCategori.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterCategori.ViewHolder holder, int position) {

        holder.nomeCategoria.setText(categoriaList.get(position).getNome());



        final List<Post> mlPosts = new ArrayList<>();
        String URL = Constantes.GET_POST+categoriaList.get(position).getId();
        RequestQueue queue = Volley.newRequestQueue(context);


        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        try {
                            for (int i = 0 ; i < response.length() ; i++){
                                JSONObject jsonObj = response.getJSONObject(i);


                                  Post post = new Post();
                                 JSONObject titlea = jsonObj.getJSONObject("title");
                                  JSONObject descA = jsonObj.getJSONObject("excerpt");
                                 post.setTitulo(titlea.get("rendered").toString());
                                 post.setImg(String.valueOf(jsonObj.getInt("featured_media")));
                                 post.setDescricao(descA.getString("rendered"));
                                 post.setLink(jsonObj.getString("link"));
                                 post.setPage_views(String.valueOf(jsonObj.getInt("page_views")));

                                 mlPosts.add(post);
                              //  Categoria categoria = new Categoria();
                              //  categoria.setId(jsonObj.getInt("id"));
                              //  categoria.setNome(jsonObj.getString("name"));
                               // categorias.add(categoria);


                            }



                           LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                           holder.reciclePost.setLayoutManager(layoutManager);
                            RecycleViewAdapterPost adapter = new RecycleViewAdapterPost(context, mlPosts);
                           holder.reciclePost.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.i("erro_8",e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("erro-Jdon",error.toString());
                    }
                }
        );


        queue.add(getRequest);


    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomeCategoria;
        RecyclerView reciclePost ;


        public ViewHolder( View itemView) {
            super(itemView);
            this.nomeCategoria = itemView.findViewById(R.id.txt_nome_categoria);
            this.reciclePost = itemView.findViewById(R.id.lista_posts);



        }
    }
}


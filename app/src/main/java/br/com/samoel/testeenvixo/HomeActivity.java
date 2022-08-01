package br.com.samoel.testeenvixo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.samoel.testeenvixo.adapter.RecycleViewAdapterCategori;
import br.com.samoel.testeenvixo.compare.ComparePost;
import br.com.samoel.testeenvixo.model.Categoria;

public class HomeActivity extends AppCompatActivity {


    private RecyclerView recyclerViewCategorias;
    private Spinner order;
    private int total;
    // private AppCompatButton btn_site;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategorias = findViewById(R.id.lista_cursos);
        order = findViewById(R.id.s_order);

        order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().equals("A - Z")){
                    carregaTodasLista(0);
                }
                if(adapterView.getSelectedItem().toString().equals("Z - A")){
                    carregaTodasLista(1);
                }
               // Log.i("clique63",adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       // btn_site = findViewById(R.id.btn_site);

      /*  btn_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse("https://coursify.me/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });*/



    }
    private void carregaTodasLista(int filtro) {

        final List<Categoria> categorias = new ArrayList<>();
        String URL = Constantes.GET_CURSOS;
        RequestQueue queue = Volley.newRequestQueue(this);


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


                              //  Post post = new Post();
                               // JSONObject titlea = jsonObj.getJSONObject("title");
                              //  JSONObject descA = jsonObj.getJSONObject("excerpt");
                               // post.setTitulo(titlea.get("rendered").toString());
                               // post.setImg(String.valueOf(jsonObj.getInt("featured_media")));
                               // post.setDescricao(descA.getString("rendered"));
                               // post.setLink(jsonObj.getString("link"));

                                Categoria categoria = new Categoria();
                                categoria.setId(jsonObj.getInt("id"));
                                categoria.setNome(jsonObj.getString("name"));

                              categorias.add(categoria);

                          //    Log.i("foi_55", String.valueOf(categorias.get(0).getVisualizacao()));
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.VERTICAL,false);
                           recyclerViewCategorias.setLayoutManager(layoutManager);
                            RecycleViewAdapterCategori adapter = new RecycleViewAdapterCategori(HomeActivity.this, categorias,filtro);
                            recyclerViewCategorias.setAdapter(adapter);

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

    private void retornaVisualizacao(int id) {
        String URL = Constantes.GET_POST+id;
        final List valor = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);


        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        try {
                            int total = 0;
                            for (int i = 0 ; i < response.length() ; i++){
                                JSONObject jsonObj = response.getJSONObject(i);
                                total +=   jsonObj.getInt("page_views");
                            }
                            getvalor(total);
                            Log.i("primeiro", "depois");

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

    private void getvalor(int total) {

        this.total = total;
    }

}
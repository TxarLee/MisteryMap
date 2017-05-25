package com.marluki.misterymap;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.marluki.misterymap.model.Comentario;
import com.marluki.misterymap.model.ComentarioViewModel;
import com.marluki.misterymap.model.ObjetoMapa;
import com.marluki.misterymap.provider.DatuBaseKontratua;

import java.util.ArrayList;

public class DetallesActivity extends AppCompatActivity {

    private ArrayList<String> arrayComentarios;
    private SearchAdapter searchAdapter;
    private ContentResolver resolver;
    private Comentario comentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        String id=getIntent().getStringExtra("id");
        Uri uri= DatuBaseKontratua.Objetos_mapa.crearUriParaComentario(id);

        resolver=getContentResolver();

        Cursor c=resolver.query(uri,null,null,null,null);
        ArrayList<ComentarioViewModel> comentarioViewModelArrayList=new ArrayList<ComentarioViewModel>();

        while(c.moveToNext()){
            ComentarioViewModel comentarioViewModel=new ComentarioViewModel(
                    c.getString(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("objeto_id")),
                    c.getString(c.getColumnIndex("usuario_id")),
                    c.getString(c.getColumnIndex("comentario_id")),
                    c.getString(c.getColumnIndex("texto")),
                    c.getString(c.getColumnIndex("fecha")),
                    c.getString(c.getColumnIndex("nombre_usuario"))
            );
            comentarioViewModelArrayList.add(comentarioViewModel);

        }
        CursorRecyclerAdapter adapter=new CursorRecyclerAdapter(comentarioViewModelArrayList);

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.objetoContentRecyclerView);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());






    }
}
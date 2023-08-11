package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarContatosActivity extends AppCompatActivity {

    private ListView listView;
    private ContatoDAO dao;
    private List<Contato> contatos;
    private List<Contato> contatosFiltrados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contatos);

        listView = findViewById(R.id.lista_contatos);
        dao = new ContatoDAO(this);
        contatos = dao.obterTodos();
        contatosFiltrados.addAll(contatos);
        ArrayAdapter<Contato> adaptador = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatosFiltrados);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurarContato(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);

    }

    public void procurarContato(String nome){
        contatosFiltrados.clear();
        for (Contato a : contatos){
            if(a.getNome().toLowerCase().contains(nome.toLowerCase())){
                contatosFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contatoExcluir = contatosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o contato?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        contatosFiltrados.remove(contatoExcluir);
                        contatos.remove(contatoExcluir);
                        dao.excluir(contatoExcluir);
                        listView.invalidateViews();

                    }
                }).create();
        dialog.show();

    }

    public void cadastrar(MenuItem item){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contatoAtualizar = contatosFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("contato", contatoAtualizar);
        startActivity(it);


    }

    @Override
    public void onResume(){
        super.onResume();
        contatos = dao.obterTodos();
        contatosFiltrados.clear();
        contatosFiltrados.addAll(contatos);
        listView.invalidateViews();
    }
}
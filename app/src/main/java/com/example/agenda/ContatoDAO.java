package com.example.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ContatoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());
        values.put("tipo", contato.getTipo());
        values.put("nota", contato.getNota());
        return banco.insert("contato", null, values);
    }

    public List<Contato> obterTodos(){
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = banco.query("contato", new String[]{"id", "nome", "email", "telefone", "tipo", "nota"},null, null, null, null, null);
        while (cursor.moveToNext()){
            Contato a = new Contato();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setEmail(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            a.setTipo(cursor.getString(4));
            a.setNota(cursor.getString(5));
            contatos.add(a);
        }
        return contatos;
    }

    public void excluir(Contato a){
        banco.delete("contato", "id = ?", new String[]{a.getId().toString()});

    }

    public void atualizar(Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("telefone", contato.getTelefone());
        values.put("tipo", contato.getTipo());
        values.put("nota", contato.getNota());
        banco.update("contato", values, "id = ?", new String[]{contato.getId().toString()});
    }
}

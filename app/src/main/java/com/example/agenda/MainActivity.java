package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText tipo;
    private EditText nota;
    private ContatoDAO dao;
    private Contato contato = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        telefone = findViewById(R.id.editTelefone);
        tipo = findViewById(R.id.editTipo);
        nota = findViewById(R.id.editNota);
        dao = new ContatoDAO(this);

        Intent it = getIntent();
        if(it.hasExtra("contato")){
            contato = (Contato) it.getSerializableExtra("contato");
            nome.setText(contato.getNome());
            email.setText(contato.getEmail());
            telefone.setText(contato.getTelefone());
            tipo.setText(contato.getTipo());
            nota.setText(contato.getNota());
        }
    }

    public void salvar(View view){

        if (contato == null) {
            contato = new Contato();
            contato.setNome(nome.getText().toString());
            contato.setEmail(email.getText().toString());
            contato.setTelefone(telefone.getText().toString());
            contato.setTipo(tipo.getText().toString());
            contato.setNota(nota.getText().toString());
            long id = dao.inserir(contato);
            Toast.makeText(this, "Contato inserido com id: " + id, Toast.LENGTH_SHORT).show();
        }else {
            contato.setNome(nome.getText().toString());
            contato.setEmail(email.getText().toString());
            contato.setTelefone(telefone.getText().toString());
            contato.setTipo(tipo.getText().toString());
            contato.setNota(nota.getText().toString());
            dao.atualizar(contato);
            Toast.makeText(this, "Contato foi atualizado", Toast.LENGTH_SHORT).show();
        }
    }
}
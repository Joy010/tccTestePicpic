package com.example.testepicpic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testepicpic.R;
import com.example.testepicpic.config.ConfigFirebase;
import com.example.testepicpic.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class tela_cadastro2 extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private EditText confSenha;
    private Button btnPronto2;

    FirebaseAuth autenticacao;

    private Usuario user = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro2);

        email = findViewById(R.id.edtEmail);
        senha = findViewById(R.id.edtSenha);
        confSenha = findViewById(R.id.edtConfirmS);
        btnPronto2 = findViewById(R.id.button9);

        btnPronto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = email.getText().toString();
                String textoSenha = senha.getText().toString();
                String textoConfSenha = confSenha.getText().toString();

                if(!textoEmail.isEmpty()) {

                    if(!textoSenha.isEmpty()) {

                        if(!textoConfSenha.isEmpty()) {

                            if(textoSenha.equals(textoConfSenha)) {
                                user.setEmail(textoEmail);
                                user.setSenha(textoSenha);

                                cadastrar();
                            } else  {
                                Toast.makeText(tela_cadastro2.this, "As senhas não estão iguais", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(tela_cadastro2.this, "Confirme sua senha não poder estar vazio", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(tela_cadastro2.this, "Senha não pode estar vazio", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(tela_cadastro2.this, "Email não pode estar vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /* lembrar de inserir mais tarde o ultimo botão "pronto" das telas de cadastro, para mão haver conlito de criação de conta */

    public void cadastrar() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                user.getEmail(), user.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent telaCad02  = new Intent(tela_cadastro2.this , tela_cadastro3.class);
                    startActivity(telaCad02);
                } else {

                String excessao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excessao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excessao = "Digite um email válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excessao = "Esta cinta já foi cadstrada";
                    } catch (Exception e) {
                        excessao = "Erro ao cadastrar: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(tela_cadastro2.this, excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
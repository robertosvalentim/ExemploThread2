package com.r2solution.exemplothread2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SlowActivity extends AppCompatActivity {
    private static int tempoRestanteSegundos = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slow);

        // Criando referências para os componentes gráficos na tela
        final TextView msgAguarde = (TextView)findViewById(R.id.msgAguarde);
        final ProgressBar barraDeProgresso = (ProgressBar)findViewById(R.id.barraDeProgresso);
        final TextView msgTempoRestante = (TextView)findViewById(R.id.tempoRestante);
        final TextView msgFinalzado = (TextView)findViewById(R.id.msgFinalizado);
        final LinearLayout layoutBase = (LinearLayout) findViewById(R.id.layout);

        // Exibindo o tempo inicial de espera
        msgTempoRestante.setText("Tempo restante: " + tempoRestanteSegundos + " seg");

        // Inicia a tarefa que demanda muito tempo em uma thread auxiliar
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(tempoRestanteSegundos >= 0){
                        // Simulação do processo que dura 10 segundos
                        Thread.sleep(1000);
                        tempoRestanteSegundos--;
                        msgTempoRestante.post(new Runnable() {
                            @Override
                            public void run() {
                                // Atualizar o tempo restante.
                                msgTempoRestante.setText("Tempo restante: " + tempoRestanteSegundos + " seg");
                            }
                        });
                    }

                    // Após a finalização do processo, podemos atualizar
                    // a interface gráfica, mas isso deve ser feito a partir
                    // da UI thread. Para isso, usamos o método View.post
                    layoutBase.post(new Runnable() {
                        @Override
                        public void run() {
                            // Removendo mensagens de espera e barra de progresso.
                            msgAguarde.setVisibility(View.GONE);
                            barraDeProgresso.setVisibility(View.GONE);
                            msgTempoRestante.setVisibility(View.GONE);
                            // Exibir a mensagem de tarefa finalizada
                            msgFinalzado.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
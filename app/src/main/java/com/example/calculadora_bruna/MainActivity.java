package com.example.calculadora_bruna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context; //importando daquela biblioteca
import org.mozilla.javascript.Scriptable;


import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView txtResultado, txtSolucao;
    MaterialButton btnC,btnBracketOpen,btnBracketClose,btnDivisao, btnMultiplicacao,btnAdd,btnSub, btnIgual;
    MaterialButton btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    MaterialButton btnAC, btnPonto;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResultado=findViewById(R.id.result_tv);
        txtSolucao=findViewById(R.id.solution_tv);

        associarBotoes(btnC,R.id.button_c);
        associarBotoes(btnBracketOpen,R.id.button_open_bracket);
        associarBotoes(btnBracketClose,R.id.button_close_bracket);
        associarBotoes(btnDivisao,R.id.button_divide);
        associarBotoes(btnMultiplicacao,R.id.button_multiplicacao);
        associarBotoes(btnAdd,R.id.button_soma);
        associarBotoes(btnSub,R.id.button_subtracao);
        associarBotoes(btnIgual,R.id.button_igual);
        associarBotoes(btn0,R.id.button_0);
        associarBotoes(btn1,R.id.button_1);
        associarBotoes(btn2,R.id.button_2);
        associarBotoes(btn3,R.id.button_3);
        associarBotoes(btn4,R.id.button_4);
        associarBotoes(btn5,R.id.button_5);
        associarBotoes(btn6,R.id.button_6);
        associarBotoes(btn7,R.id.button_7);
        associarBotoes(btn8,R.id.button_8);
        associarBotoes(btn9,R.id.button_9);
        associarBotoes(btnAC,R.id.button_ac);
        associarBotoes(btnPonto,R.id.button_ponto);
    }

    //Método para associar variáveis ao layout
    void associarBotoes(MaterialButton btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //Ao clicar em qualquer botão
    @Override
    public void onClick(View v)
    {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString(); //Extrair
        //txtSolucao.setText(buttonText); //testando
        String paraCalcular = txtSolucao.getText().toString();

        if(buttonText.equals("AC")) //Se o botão for AC
        {
            txtSolucao.setText("");
            txtResultado.setText("0");
            return;
        }

        if(buttonText.equals(("=")))
        {
            txtSolucao.setText(txtResultado.getText()); //Qnd aperta = o que estiver na txt de baixo vai para cima
            //txtResultado.setText("0");
            return;
        }


        if (buttonText.equals("C"))
        {
            /*if (paraCalcular.length()<2)
            {
                txtSolucao.setText("0");
            }*/

            if (!paraCalcular.isEmpty())
            {
                paraCalcular = paraCalcular.substring(0, paraCalcular.length() - 1); // Apagar o último caractere
            }
        } else if (buttonText.matches("[0-9+-/*]")) //Se não for o "C" //buttonText é a variável que recebe a String do botão
        {
            if (!paraCalcular.isEmpty() && !buttonText.matches("[0-9]") && paraCalcular.substring(paraCalcular.length() - 1).matches("[+-/*]"))
            {

                paraCalcular = paraCalcular.substring(0, paraCalcular.length() - 1) + buttonText;
            } else {
                paraCalcular = paraCalcular + buttonText; //Qualquer outro boão será add ao de txt de cima
            }
        }


        txtSolucao.setText(paraCalcular);


        txtSolucao.setText(paraCalcular);

        String resultadoFinal = getResult(paraCalcular);

        if(!resultadoFinal.equals("Erro"))
        {
            txtResultado.setText(resultadoFinal);
        }
    }


    String getResult(String data) //, o método getResult é responsável por avaliar uma expressão matemática ou um código JavaScript que contém essas operações e retornar o resultado da avaliação.
    {
        try //da biblioteca // JavaScript possui suporte embutido para operações matemáticas,
        {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String resultadoFinal = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(resultadoFinal.endsWith(".0"))
            {
                resultadoFinal = resultadoFinal.replace(".0","");//Tirando o .0
            }
            return  resultadoFinal;
        }
        catch(Exception e)
        {
        return "Erro";
        }
    }
}

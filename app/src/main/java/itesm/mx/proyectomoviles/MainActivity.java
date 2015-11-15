package itesm.mx.proyectomoviles;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText userET = (EditText) findViewById(R.id.usuarioET);
        final EditText passET = (EditText) findViewById(R.id.contrasenaET);
        final Button entrarButton = (Button) findViewById(R.id.entrarBT);


        View.OnClickListener registro = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent (MainActivity.this, Administrador.class);
                Intent intent2 = new Intent (MainActivity.this, Staff.class);
                String mail = userET.getText().toString();
                String password = passET.getText().toString();
                if((mail.equalsIgnoreCase("a") && password.equalsIgnoreCase("a"))) {
                    intent.putExtra("username", mail);
                    intent.putExtra("password", password);

                    startActivityForResult(intent,1);
                }
                else{
                    intent.putExtra("username", mail);
                    intent.putExtra("password", password);
                    startActivityForResult(intent2,1);
                }

            }


        };

        entrarButton.setOnClickListener(registro);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

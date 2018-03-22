package info.devexchanges.gridviewmultiplechoices;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private View btnGo;
    private Button btnGuardar, btnBorrar;
    private ArrayList<String> selectedStrings;
    private static final String[] numbers = new String[680];

    AyudaBD ayudaBD;
    GridViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         for(int i = 0; i< 680; i++){
             numbers[i] = (""+(i+1));
        }

        gridView = (GridView) findViewById(R.id.grid);
        btnGo = findViewById(R.id.button);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);

        selectedStrings = new ArrayList<>();

        ayudaBD = new AyudaBD(getApplicationContext());

        adapter = new GridViewAdapter(numbers, this);
        gridView.setAdapter(adapter);

        CargarFiguras();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedStrings.remove((String) parent.getItemAtPosition(position));
                } else {
                    adapter.selectedPositions.add(position);
                    ((GridItemView) v).display(true);
                    selectedStrings.add((String) parent.getItemAtPosition(position));
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = ayudaBD.getWritableDatabase();
                ContentValues valores = new ContentValues();

                valores.put(AyudaBD.DatosFiguras.COLUMNA_ID, "1");
                valores.put(AyudaBD.DatosFiguras.COLUMN_NUMERO_FIGURA,"10");
                valores.put(AyudaBD.DatosFiguras.COLUMN_ESTADO_FIGURA,"1");

                Long idGuardado = db.insert(AyudaBD.DatosFiguras.TABLE_NAME, AyudaBD.DatosFiguras.COLUMNA_ID, valores);

                Toast.makeText(getApplicationContext(), "resultado: " + idGuardado, Toast.LENGTH_SHORT).show();

            }
        });

        //set listener for Button event
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectedItemsActivity.class);
                intent.putStringArrayListExtra("SELECTED_LETTER", selectedStrings);
                startActivity(intent);
            }
        });
    }

    public void CargarFiguras(){
        Integer position = 0;
        for (int i=0; i  < numbers.length; i ++){
            numbers[i] = ""+(i+1);
            Integer celdaSelect = 0;
            position = i;
            celdaSelect = BuscarByIdFigura(i+1);
            int selectedIndex = adapter.selectedPositions.indexOf(position);

            if(celdaSelect==1){
                //adapter.selectedPositions.remove(selectedIndex);
                ((GridItemView)adapter.getView(position, null,null)).display(true);
                //selectedStrings.remove((String) parent.getItemAtPosition(position));
            }else{
                //adapter.selectedPositions.add(position);
                ((GridItemView)adapter.getView(position, null,null)).display(false);
                //selectedStrings.add((String) parent.getItemAtPosition(position));
            }
        }
    }

    public Integer BuscarByIdFigura(int idFigura){
        SQLiteDatabase db = ayudaBD.getReadableDatabase();
        String[] projection = {AyudaBD.DatosFiguras.COLUMN_NUMERO_FIGURA, AyudaBD.DatosFiguras.COLUMN_ESTADO_FIGURA};
        String[] arqsel = {""+idFigura};
        Cursor c = db.query(AyudaBD.DatosFiguras.TABLE_NAME,
                projection,
                AyudaBD.DatosFiguras.COLUMNA_ID + "=?",
                arqsel,
                null,
                null,
                null
        );

        Integer resultado = 0;
        try{
            c.moveToFirst();
            resultado = Integer.parseInt((c.getString(2)==null)?"0":c.getString(2));
            Toast.makeText(getApplicationContext(), "resultado: " + resultado, Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
            resultado = 0;
        }


        return resultado;
    }
}

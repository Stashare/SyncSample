package ke.co.stashare.syncsample.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 18/09/2017.
 */

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }

    public void beneficiary_registration(View view) {

        LayoutInflater inflater = getLayoutInflater();

        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);

        alertDialogBuilderUserInput.setTitle("Beneficiary Registration");
        alertDialogBuilderUserInput.setMessage("Work in progress");

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        dialogBox.dismiss();
                    }
                });
        AlertDialog alertDialogAndroid= alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    public void distribution(View view) {
        LayoutInflater inflater = getLayoutInflater();

        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);

        alertDialogBuilderUserInput.setTitle("Distribution");
        alertDialogBuilderUserInput.setMessage("Work in progress");

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        dialogBox.dismiss();
                    }
                });
        AlertDialog alertDialogAndroid= alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    public void startAsses(View view) {

        Intent intent = new Intent(Dashboard.this, SelectSurvey.class);

        startActivity(intent);
    }

}

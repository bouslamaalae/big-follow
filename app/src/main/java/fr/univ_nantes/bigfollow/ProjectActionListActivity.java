package fr.univ_nantes.bigfollow;

import android.os.Bundle;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.sheets.v4.SheetsScopes;

import com.google.api.services.sheets.v4.model.*;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.univ_nantes.bigfollow.config.Config;
import fr.univ_nantes.bigfollow.model.ProjectAction;
import fr.univ_nantes.bigfollow.model.ProjectSheet;
import fr.univ_nantes.bigfollow.util.Util;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ProjectActionListActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREFS_NAME = "PrefsFile";
    private static final String PREF_ACCOUNT_NAME = "accountName";

    private static final String[] SCOPES = { SheetsScopes.SPREADSHEETS_READONLY };

    private ProjectSheet sheet;
    GoogleAccountCredential mCredential;
    @BindView(R.id.tl_project_actions) TableLayout mTlPojectApplications;

    ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_action_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.project_action_list));
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        int idProject = (int) (long) getIntent().getLongExtra(Config.EXTRA_ID_PROJECT, 0);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage(getString(R.string.calling_google_sheets_api));

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        sheet = ProjectSheet.getByIdProjectAndType(idProject, Config.TYPE_SHEET_PROJECT_ACTION_LIST);
        String date = (sheet.getUpdateAt() != null) ? Util.dateToString(sheet.getUpdateAt()) : getString(R.string.never);
        //mTvLastUpdateDate.setText(date);

        populateTableLayout();
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.error_gps_not_installed),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }


    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getSharedPreferences(PREFS_NAME, 0)
                    .getString(PREF_ACCOUNT_NAME, null);
            mCredential.setSelectedAccountName(accountName);
            getResultsFromApi();
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.info_access_to_google_account_needed),
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.error_no_network),
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    private void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                ProjectActionListActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    private void populateTableLayout() {

        List<ProjectAction> projectActions = ProjectAction.getAll();

        View header = mTlPojectApplications.getChildAt(0);
        mTlPojectApplications.removeAllViews();
        mTlPojectApplications.addView(header);

        for (ProjectAction pa : projectActions) {
            TableRow row = (TableRow) LayoutInflater.from(ProjectActionListActivity.this)
                    .inflate(R.layout.row_project_action, null);

            if (mTlPojectApplications.getChildCount() % 2 == 1) {
                row.setBackgroundResource(R.color.colorLightGrey);
            }

            ((TextView) ButterKnife.findById(row, R.id.tv_action)).setText(pa.getAction());
            ((TextView) ButterKnife.findById(row, R.id.tv_type)).setText(String.valueOf(pa.getType()));
            ((TextView) ButterKnife.findById(row, R.id.tv_order)).setText(String.valueOf(pa.getOrder()));
            ((TextView) ButterKnife.findById(row, R.id.tv_price)).setText(pa.getPrice());
            ((TextView) ButterKnife.findById(row, R.id.tv_domain)).setText(pa.getDomain());
            ((TextView) ButterKnife.findById(row, R.id.tv_step)).setText(
                    pa.getStep() != ProjectAction.STEP_NULL ? String.valueOf(pa.getStep()) : getString(R.string.no_step));
            ((TextView) ButterKnife.findById(row, R.id.tv_is_displayed)).setText(String.valueOf(pa.isDisplayed()));
            ((TextView) ButterKnife.findById(row, R.id.tv_invoicing_type)).setText(
                    pa.getInvoicingType() == ProjectAction.INVOICING_TYPE_TP ? getString(R.string.tp) : getString(R.string.fo));
            ((TextView) ButterKnife.findById(row, R.id.tv_days_count)).setText(pa.getDaysCount());
            ((TextView) ButterKnife.findById(row, R.id.tv_start_at)).setText(pa.getStartAt());
            ((TextView) ButterKnife.findById(row, R.id.tv_end_at)).setText(pa.getEndAt());
            ((TextView) ButterKnife.findById(row, R.id.tv_cost_by_day)).setText(pa.getCostByDay());
            ((TextView) ButterKnife.findById(row, R.id.tv_project_manager)).setText(pa.getProjectManager());
            ((TextView) ButterKnife.findById(row, R.id.tv_product_owner)).setText(pa.getProductOwner());
            ((TextView) ButterKnife.findById(row, R.id.tv_product_user)).setText(pa.getProductUser());

            mTlPojectApplications.addView(row);
        }
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<List<Object>>> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .build();
        }

        /**
         * Background task to call Google Sheets API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<List<Object>> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<List<Object>> result) {
            mProgress.hide();
            if (result == null || result.size() == 0) {
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.info_no_results_returned),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.info_data_retrieved),
                        Toast.LENGTH_SHORT)
                        .show();

                saveData(result);
                populateTableLayout();
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            ProjectActionListActivity.REQUEST_AUTHORIZATION);
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.error_occured) + mLastError.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.info_request_cancelled),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        /**
         * Fetch all the data in a spreadsheet:
         * https://spreadsheets.google.com/feeds/list/1yZDsV2AwkS5getgjXIFKYkSLZprf8wee6S95_QUyOmI/1/public/values
         * @return All the data
         * @throws IOException
         */
        private List<List<Object>> getDataFromApi() throws IOException {
            ValueRange response = this.mService.spreadsheets().values()
                    .get(sheet.getProject().getSpreadsheetId(), sheet.getName() + sheet.getRange())
                    .execute();

            return response.getValues();
        }

        private void saveData(List<List<Object>> data) {
            ProjectAction projectAction;
            ActiveAndroid.beginTransaction();
            try {
                for (List row : data) {
                    projectAction = ProjectAction.convertToProjectAction(row);
                    projectAction.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();

                sheet.setUpdateAt(new Date());
                sheet.save();

            }
        }
    }
}

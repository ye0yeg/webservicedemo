package com.ye0yeg.webservicedemo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected static final int TESTBEGIN = 0;
	private Button bt_query;
	private TextView tv_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		if (Build.VERSION.SDK_INT >= 11) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
					.penaltyLog().penaltyDeath().build());
		}
	}
		
	private void initView() {
		tv_show = (TextView) findViewById(R.id.tv_result);
		bt_query = (Button) findViewById(R.id.bt_submit);
	}
	
	private Handler mhandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TESTBEGIN:
				getInfo();
				break;

			default:
				break;
			}
			
		};
		
	};

	private void initEvent() {
		bt_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mhandler.sendEmptyMessage(TESTBEGIN);
			}
		});

	}

	protected void getInfo() {
		// 命名空间
				String nameSpace = "http://tempuri.org/";
				// 调用的方法名称
				String methodName = "Add_OM_Normal";
				// EndPoint
				String endPoint = "http://110.80.36.242:60000/omms/WebService/WS_APP.asmx";
				// SOAP Action
				String soapAction = "http://tempuri.org/Add_OM_Normal";
				// 指定WebService的命名空间和调用的方法名
				SoapObject rpc = new SoapObject(nameSpace, methodName);

				// 设置需调用WebService接口需要传入的参数
				rpc.addProperty("StationCode", "350600000001004");
				rpc.addProperty("DeviceCode", "1");
				rpc.addProperty("DataTime", "2016-02-02 19:00:00");
				rpc.addProperty("Pre_ReadOMData", "1");
				rpc.addProperty("Pre_Tool", "1");
				rpc.addProperty("Sys_Power", "false");
				rpc.addProperty("Sys_Conn", "false");
				rpc.addProperty("Sys_PLC", "false");
				rpc.addProperty("Sys_Pump", "false");
				rpc.addProperty("Sys_Water", "false");
				rpc.addProperty("Device_Display", "false");
				rpc.addProperty("Device_Fault", "false");
				rpc.addProperty("Device_Lines", "false");
				rpc.addProperty("Device_Check", "false");
				rpc.addProperty("Cycle_ClearDevice", "false");
				rpc.addProperty("Cycle_ClearLine", "false");
				rpc.addProperty("Cycle_DisWater", "false");
				rpc.addProperty("Cycle_ReplaceReagent", "false");
				rpc.addProperty("Cycle_ReplaceInvitation", "15");
				rpc.addProperty("Cycle_Clear", "15");
				rpc.addProperty("Cycle_StationData", "16");
				rpc.addProperty("Other", "cc");
				rpc.addProperty("Exception", "18");
				rpc.addProperty("ReplaceInvitation", "19");
				rpc.addProperty("ReplaceInvitation_Remark", "20");
				rpc.addProperty("LeaveTime", "2016-02-02");
				rpc.addProperty("ServiceTime", "29");
				rpc.addProperty("OM_EP", "23");
				rpc.addProperty("OM_Man", "24");
				rpc.addProperty("SignDate", "2016-02-02");
				rpc.addProperty("OM_EPMan_Sign", "OM_EPMan_Sign");
				rpc.addProperty("OM_Man_Sign", "OM_Man_Sign");

		
		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			System.out.println("未调用");
		}
		try {
			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			String result = null;
			if (null != object) {
				result = object.getProperty(0).toString();
				System.out.println("结果" + result);
				tv_show.setText(result);
				System.out.println(result);
			}
			// 将WebService返回的结果显示在TextView中
		} catch (Exception e) {
			System.out.println("未成功");
		}

	}



}

package com.jgdsun.ba.utils;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyDialog extends JFrame {
	private JFrame jframe;
	public MyDialog(JFrame jframe)
	{
		this.jframe=jframe;
		// UIManager.put("OptionPane.messageFont",new Font("����",Font.PLAIN+Font.BOLD,13));
	}
         public String showInputDialog(String value){ 
//	 ��ʾһ��Ҫ���û����� String �ĶԻ���
         return JOptionPane.showInputDialog(value); 
         } 
         public void showMessageDialog(String title,String message){
//	 ��ʾһ������Ի��򣬸öԻ�����ʾ�� message Ϊ 'alert'�� 
        	 
         JOptionPane.showMessageDialog(jframe, message, title, JOptionPane.ERROR_MESSAGE);
         }
         public void showOkMessageDialog(String title,String message){
//	 ��ʾһ������Ի��򣬸öԻ�����ʾ�� message Ϊ 'alert'��
        JOptionPane.showMessageDialog(jframe, message, title, JOptionPane.INFORMATION_MESSAGE);
        }

         public boolean showOptionDialog(String message,String ok,String cancel){
//	 ʾһ������Ի����� options Ϊ OK��CANCEL��title Ϊ 'Warning'��message Ϊ 'Click OK to continue'��
         String[] options = { ok, cancel };
         int result=JOptionPane.showOptionDialog(jframe, message, "��ѡ��",
         JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
         null, options, options[0]);
         if(result==0)
         return true;
         else
         return false;
         }
         public String possibleValues(String[] a){
//	 ��ʾһ��Ҫ���û�ѡ�� String �ĶԻ���
//	 String[] possibleValues = { "First", "Second", "Third" };
         String selectedValue = (String) JOptionPane.showInputDialog(jframe,
         "Choose one", "Input",
         JOptionPane.INFORMATION_MESSAGE, null,
         a, a[0]);
         return selectedValue;
         }
         }

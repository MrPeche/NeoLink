package com.example.neolink_app.clases.LoginFirebase;

public class UsuarioNeoL {
    private String uid;
    private String correo;
    private int errorslot=0; //1=El correo no existe, 2=La contraseña es incorrecta, 3=No se pudo conectar con el servidor
    public UsuarioNeoL(String uid,String correo){
        this.uid = uid;
        this.correo = correo;
    }
    public UsuarioNeoL(int errorslot){
        this.uid = null;
        this.correo = null;
        this.errorslot= errorslot;
    }
    public String giveUID(){
        return this.uid;
    }
    public String giveMail(){
        return this.correo;
    }
    public int Validate(){
        return errorslot;
    }
}

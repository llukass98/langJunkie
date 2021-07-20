package ru.lukas.langjunkie;

class Api {

    private static Api instance;
    private String uri;
    private String response;

    private Api () {}

    public static Api getInstance() {
	if (instance == null) {
	    instance = new Api();
	}

	return instance;
    }

    public void setUri(String uri) {
	this.uri = uri;
    }

    public String getUri() {
	return uri;
    }

}

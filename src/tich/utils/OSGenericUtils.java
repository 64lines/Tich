package tich.utils;

public class OSGenericUtils {
	public static void openBrowser(String url){
		if(!java.awt.Desktop.isDesktopSupported())
            System.err.println("Desktop is not supported (fatal)");

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if(!desktop.isSupported(java.awt.Desktop.Action.BROWSE))
            System.err.println("Desktop doesn't support the browse action (fatal)");
        
        try {
	        java.net.URI uri = new java.net.URI(url);
	        desktop.browse(uri);
        }
        catch (Exception e){}
	}
}

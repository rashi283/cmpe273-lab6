package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        List<CacheServiceInterface> servers = new ArrayList<CacheServiceInterface>();
        servers.add(new DistributedCacheService("http://localhost:3000"));
        servers.add(new DistributedCacheService("http://localhost:3001"));
        servers.add(new DistributedCacheService("http://localhost:3002"));
        
        String[] value = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        int size =servers.size();
        int serverSelected;
        
        for(int i=1; i<=10; i++)
        {
        	serverSelected = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), 
        			servers.size());
        	servers.get(serverSelected).put(i, value[i-1]);
        	System.out.println("Put (" + i + "  => " + value[i-1] + ") => Server http://localhost:300" + serverSelected) ;
        }	
        
        for(int i=1;i<=10;i++)
		{
			serverSelected = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), 
					servers.size());
			System.out.println("Get (" + i + ") =>" + servers.get(serverSelected).get(i) + ") => Server http://localhost:300" 
					+ serverSelected );
		
		}
        System.out.println("Existing Cache Client...");
    }   

}
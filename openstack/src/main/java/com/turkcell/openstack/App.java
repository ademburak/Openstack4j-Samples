package com.turkcell.openstack;

import java.io.File;
import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.options.CreateUpdateContainerOptions;
import org.openstack4j.openstack.OSFactory;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		OSClient os;
		try {
			Identifier domainIdentifier = Identifier.byName("d1");
			Identifier projectIdentifier = Identifier.byName("p1");
			os = OSFactory.builderV3()
					.endpoint("http://10.252.174.176:5000/v3")
					.credentials("u1", "testing", domainIdentifier)
					.scopeToProject(projectIdentifier, domainIdentifier)
					.authenticate();

			updateContainer(os);

			uploadFile(os);

		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void listContainers(OSClient os) {

		List<? extends SwiftContainer> containers = os.objectStorage()
				.containers().list();

		for (SwiftContainer container : containers) {
			System.out.println(container);
		}
	}

	public static void updateContainer(OSClient os) {

		os.objectStorage()
				.containers()
				.create("c1",
						CreateUpdateContainerOptions.create()
								.accessAnybodyRead());

	}

	public static void uploadFile(OSClient os) {
		File file = new File("D:/personal/burak_cakmak.jpg");

		String etag = os.objectStorage().objects()
				.put("c1", file.getName(), Payloads.create(file));
		System.out.println("etag: " + etag);
	}
}

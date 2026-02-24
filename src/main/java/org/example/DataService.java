package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataService implements CommandLineRunner {

    private WarehouseRepository warehouseRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    public DataService(WarehouseRepository warehouseRepository, ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void run(String... args){

        Warehouse warehouse1 = new Warehouse();
        warehouse1.setWarehouseID("001");
        warehouse1.setWarehouseName("Linz Bahnhof");
        warehouse1.setWarehouseAddress("Bahnhofsstrasse 27/9");
        warehouse1.setWarehousePostalCode("4020");
        warehouse1.setWarehouseCity("Linz");
        warehouse1.setWarehouseCountry("Austria");
        warehouse1.setTimestamp(LocalDateTime.now());
        warehouseRepository.save(warehouse1);


        Warehouse warehouse2 = new Warehouse();
        warehouse2.setWarehouseID("002");
        warehouse2.setWarehouseName("Wien Bahnhof");
        warehouse2.setWarehouseAddress("Südtirolerplatz");
        warehouse2.setWarehousePostalCode("1100");
        warehouse2.setWarehouseCity("Wien");
        warehouse2.setWarehouseCountry("Austria");
        warehouse2.setTimestamp(LocalDateTime.now());
        warehouseRepository.save(warehouse2);


        /**
         * PRODUKTE
         */

        Product p1 = new Product();
        p1.setProductID("00-443175");
        p1.setProductName("Bio Orangensaft Sonne");
        p1.setProductCategory("Getraenk");
        p1.setProductQuantity(2500);
        p1.setProductUnit("Packung 1L");
        p1.setWarehouse(warehouse1);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setProductID("00-871895");
        p2.setProductName("Bio Apfelsaft Gold");
        p2.setProductCategory("Getraenk");
        p2.setProductQuantity(3420);
        p2.setProductUnit("Packung 1L");
        p2.setWarehouse(warehouse1);
        productRepository.save(p2);

        Product p3 = new Product();
        p3.setProductID("00-123456");
        p3.setProductName("Mineralwasser");
        p3.setProductCategory("Getraenk");
        p3.setProductQuantity(5000);
        p3.setProductUnit("Flasche 1L");
        p3.setWarehouse(warehouse1);
        productRepository.save(p3);

        Product p4 = new Product();
        p4.setProductID("00-654321");
        p4.setProductName("Cola");
        p4.setProductCategory("Getraenk");
        p4.setProductQuantity(4200);
        p4.setProductUnit("Dose 0.5L");
        p4.setWarehouse(warehouse1);
        productRepository.save(p4);

        Product p5 = new Product();
        p5.setProductID("00-998877");
        p5.setProductName("Eistee");
        p5.setProductCategory("Getraenk");
        p5.setProductQuantity(3100);
        p5.setProductUnit("Flasche 1L");
        p5.setWarehouse(warehouse1);
        productRepository.save(p5);




        Product p6 = new Product();
        p6.setProductID("00-112233");
        p6.setProductName("Milch");
        p6.setProductCategory("Milchprodukte");
        p6.setProductQuantity(2800);
        p6.setProductUnit("Packung 1L");
        p6.setWarehouse(warehouse2);
        productRepository.save(p6);

        Product p7 = new Product();
        p7.setProductID("00-445566");
        p7.setProductName("Butter");
        p7.setProductCategory("Milchprodukte");
        p7.setProductQuantity(1500);
        p7.setProductUnit("250g");
        p7.setWarehouse(warehouse2);
        productRepository.save(p7);

        Product p8 = new Product();
        p8.setProductID("00-778899");
        p8.setProductName("Joghurt");
        p8.setProductCategory("Milchprodukte");
        p8.setProductQuantity(3600);
        p8.setProductUnit("Becher 200g");
        p8.setWarehouse(warehouse2);
        productRepository.save(p8);

        Product p9 = new Product();
        p9.setProductID("00-667788");
        p9.setProductName("Vollkornbrot");
        p9.setProductCategory("Backwaren");
        p9.setProductQuantity(1800);
        p9.setProductUnit("Laib");
        p9.setWarehouse(warehouse2);
        productRepository.save(p9);

        Product p10 = new Product();
        p10.setProductID("00-334455");
        p10.setProductName("Croissant");
        p10.setProductCategory("Backwaren");
        p10.setProductQuantity(2200);
        p10.setProductUnit("Stueck");
        p10.setWarehouse(warehouse2);
        productRepository.save(p10);


        // Test find warehouse
        Optional<Warehouse> foundWarehouse =
                warehouseRepository.findByWarehouseID("001");

        foundWarehouse.ifPresent(w ->
                System.out.println("Found Warehouse: " + w.getWarehouseName())

        );

        // Test find product by warehouseID + productID
        Optional<Product> foundProduct =
                productRepository.findByWarehouse_WarehouseIDAndProductID("001", "00-443175");

        foundProduct.ifPresent(p ->
                System.out.println("Found Product: " + p.getProductName())
        );

        // Test update
        foundWarehouse.ifPresent(w -> {
            w.setWarehouseCity("Graz");
            warehouseRepository.save(w);
            System.out.println("Updated City: " + w.getWarehouseCity());
        });

        // Test purchase count
        System.out.println("Total Purchases: " + purchaseRepository.count());


        // CREATE 30 PURCHASES
        for (int i = 1; i <= 30; i++) {

            Purchase purchase = new Purchase();
            purchase.setAmount(5 + i);
            purchase.setPurchaseTime(LocalDateTime.now());

            // z.B. immer Produkt p1 im Warehouse1 verkaufen
            purchase.setProduct(p1);
            purchase.setWarehouse(warehouse1);

            purchaseRepository.save(purchase);
        }

        System.out.println("Inserted 30 purchases.");
        System.out.println("Total Purchases: " + purchaseRepository.count());


        /**
         * VERTIEFUNG!
         */
        for (int i = 1; i <= 300; i++) {

            Purchase purchase = new Purchase();

            purchase.setAmount((int)(Math.random() * 20) + 1);
            purchase.setPurchaseTime(
                    LocalDateTime.now().minusDays((int)(Math.random() * 180))
            );

            purchase.setProduct(p1);
            purchase.setWarehouse(warehouse1);

            purchaseRepository.save(purchase);
        }

    }


}

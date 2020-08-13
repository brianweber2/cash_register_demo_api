# Cash Register Demo API

## Setup
1. Clone to the repository and install dependencies using Gradle in ItelliJ.
2. Start the application using *bootRun* under Gradle tool window.
3. Send a test GET request to http://localhost:8080 to ensure everything is working.

```
{
  "_links": {
    "products": {
      "href": "http://localhost:8080/products"
    },
    "transactions": {
      "href": "http://localhost:8080/transactions"
    },
    "customers": {
      "href": "http://localhost:8080/customers"
    },
    "items": {
      "href": "http://localhost:8080/items"
    },
    "profile": {
      "href": "http://localhost:8080/profile"
    }
  }
}
```

## Creating a transaction that returns the itemized receipt
First get the created user's ID by calling:

```
curl --request GET \
  --url http://localhost:8080/customers
```

Use that ID and place it in the url as a query param:

```
curl --request POST \
  --url 'http://localhost:8080/transactions?customer_id=6' \
  --header 'content-type: application/json' \
  --data '[
	{
		"id": 1,
		"name": "Scott 1000 Septic Safe Toilet Paper",
		"sku": "6392261230",
		"defaultPrice": 13.99,
		"discountedPrice": 9.99
	},
	{
		"id": 2,
		"sku": "9862865625",
		"name": "Eggs",
		"defaultPrice": 4.99,
		"discountPrice": 3.99
	},
	{
		"id": 3,
		"sku": "1711712464",
		"name": "Orange Juice",
		"defaultPrice": 5.99,
		"discountPrice": 5.49
	},
	{
		"id": 4,
		"sku": "2494535318",
		"name": "Bananas",
		"defaultPrice": 2.99,
		"discountPrice": 1.99
	},
	{
		"id": 5,
		"sku": "2817733627",
		"name": "Swifter",
		"defaultPrice": 24.99,
		"discountPrice": 22.99
	},
	{
		"id": 4,
		"sku": "2494535318",
		"name": "Bananas",
		"defaultPrice": 2.99,
		"discountPrice": 1.99
	}
]'
```

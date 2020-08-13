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

Use that ID and place it in the url as a query param to call the create transaction endpoint with a payload of product ids:

```
curl --request POST \
  --url 'http://localhost:8080/transactions?customer_id=6' \
  --header 'content-type: application/json' \
  --data '[
	{
		"id": 1,
	},
	{
		"id": 2,
	} ...
]'
```

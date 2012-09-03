# Example usage
## From class source
java -cp . proxy.jProxy

## From jar

java -jar MockyProxy.jar /path/to/your/jar/config.json

# Example config.json

```javascript
{

    "port": 10000,
	"mocks": [
		{
			"url":"http://example.com/stub.json",
			"stubFile":"/path/to/mystub/stub.json",
			"responseCode":500
		}
	]

}
```

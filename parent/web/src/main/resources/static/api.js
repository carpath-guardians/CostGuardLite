export async function getReport(selectedValues) {
    const response = await fetch(`/analyze?rules=${selectedValues.join()}`)

    if (!response.ok) {
        throw new Error('Failed to fetch data')
    }

    const data = await response.json()

    return {
        selected: selectedValues,
        apiResponse: data
    }
}
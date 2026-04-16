interface Props {
    magnitude: string
    time: string
    setMagnitude: (value: string) => void
    setTime: (value: string) => void
    onFilter: (magnitude?: number, time?: number) => void
    onReset: () => void
}

export default function EarthquakeFilter({ magnitude, time, setMagnitude, setTime, onFilter, onReset }: Props) {

    const handleFilter = () => {
        onFilter(
            magnitude ? Number(magnitude) : undefined,
            time ? new Date(time).getTime(): undefined
        )
    }

    return (
        <div className="filter">
            <div className="filter-group">
                <label>Min Magnitude</label>
                <input
                    type="number"
                    placeholder="e.g. 2.0"
                    value={magnitude}
                    onChange={(e) => setMagnitude(e.target.value)}
                />
            </div>

            <div className="filter-group">
                <label>Time After</label>
                <input
                    type="datetime-local"
                    value={time}
                    onChange={(e) => setTime(e.target.value)}
                />
            </div>

            <button onClick={handleFilter} style={{ marginLeft: "10px" }}>
                Filter
            </button>

            <button onClick={onReset} style={{ marginLeft: "10px" }}>
                Reset
            </button>
        </div>
    )
}